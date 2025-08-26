package com.bucket.hch.app.service;

import com.bucket.hch.app.aggregate.AccountStatus;
import com.bucket.hch.app.aggregate.bucketlist;
import com.bucket.hch.app.repository.BucketRepository;

import java.util.ArrayList;
import java.util.List;

public class BucketService {

    private final BucketRepository bucketRepository;

    public BucketService() {
        bucketRepository = new BucketRepository();
    }

    public void findAllBuckets(){
        ArrayList<bucketlist> result = bucketRepository.findAllBuckets();

        if (!result.isEmpty()) {
            System.out.println("Service에서 조회 확인: ");
            for (bucketlist bucket : result) {
                System.out.println("bucket = " + bucket);
            }
        } else {
            System.out.println("버킷리스트가 없습니다.");
        }
    }

    public bucketlist findBucketBy(int bucket_id){
        bucketlist findBucket = bucketRepository.findBucketBy(bucket_id);
        if (findBucket != null) {   // 버킷아이디로 조회 되는 경우
            System.out.println("버킷리스트아이디로 조회 성공: " + findBucket);
        } else {                    // 안되는 경우
            System.out.println(bucket_id + "해당 버킷리스트번호가 존재하지 않습니다.");
        }
        return findBucket;
    }

//    public List<bucketlist> findBucketByTitleOrContents(String title, String contents){
//        List<bucketlist> findBucket = bucketRepository.findBucketByTitleOrContents(title, contents);
//        if (!findBucket.isEmpty()) {   // 버킷아이디로 조회 되는 경우
//            System.out.println("버킷리스트 조회 성공: ");
//            for (bucketlist bucket : findBucket) {
//                System.out.println(bucket);
//            }
//        } else {                    // 안되는 경우
//            System.out.println("일치하는 제목과 내용이 없습니다.");
//        }
//        return findBucket;
//    }
public List<bucketlist> findBucketByTitleOrContents(String title, String contents) {
    return bucketRepository.findBucketByTitleOrContents(title, contents);
}

public void registBucket(bucketlist registBucket){

        /* 설명. 버킷리스트 id 생성(long) */
        long LastBucketNo = bucketRepository.findLastBucketNo();
        registBucket.setBucket_id(LastBucketNo + 1);

        /* 설명. 버킷리스트 활성화 상태 추가 */
        registBucket.setAccountStatus(AccountStatus.ACTIVE);

        /* 설명. DML의 성공 여부는 int 값으로 돌아옴 */
        int result = bucketRepository.registBucket(registBucket);

        if (result == 1) {
            System.out.println(registBucket.getBucket_id() + "버킷리스트 작성 성공!");
        } else {
            System.out.println("버킷리스트 작성 실패");
        }
    }

    public bucketlist findBucketForMod(int bucket_id){
        bucketlist selecteBucketList = bucketRepository.findBucketBy(bucket_id);

        bucketlist copyBucket = null;
        if (selecteBucketList != null) {

        }
        return copyBucket;
    }

    public void modifyBucket(bucketlist reformedBucket) {
        int result = bucketRepository.modifyBucket(reformedBucket);
        if (result == 1) {
            System.out.println("버킷리스트 수정 성공: " + reformedBucket);
        } else {
            System.out.println("버킷리스트 수정 실패: 해당 ID가 존재하지 않습니다.");
        }
    }

    public void removeBucket(long bucket_id) {
        int result = bucketRepository.removeBucket(bucket_id);
        if (result == 1) {
            System.out.println("버킷리스트 삭제 성공: ID " + bucket_id);
        } else {
            System.out.println("버킷리스트 삭제 실패: 해당 ID가 존재하지 않습니다.");
        }
    }


}
