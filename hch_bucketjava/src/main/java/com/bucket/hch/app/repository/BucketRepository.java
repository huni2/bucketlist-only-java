package com.bucket.hch.app.repository;

import com.bucket.hch.app.aggregate.bucketlist;
import com.bucket.hch.app.aggregate.AccountStatus;
import com.bucket.hch.app.stream.MyObjectOutput;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BucketRepository {


    private final ArrayList<bucketlist> bucket = new ArrayList<>();
    private final File file = new File("src/main/java/com/bucket/hch/app/db/bucketlistDB.dat");

    public BucketRepository() {
        if (file.exists()) {
            ArrayList<bucketlist> defaultbucketList = new ArrayList<>();
            defaultbucketList.add(new bucketlist(1,"세계일주", "1년동안 5대륙 여행하기", LocalDateTime.now().minusDays(21),120, AccountStatus.ACTIVE,1,"asd"));
            defaultbucketList.add(new bucketlist(2,"마라톤완주", "하프 -> 풀코스 순차 도전", LocalDateTime.now().minusDays(14),85, AccountStatus.ACTIVE,2,"qwe"));
            defaultbucketList.add(new bucketlist(3,"자격증 3개 취득", "정처기,sql,리눅스master 취득하기", LocalDateTime.now().minusDays(40),39, AccountStatus.ACTIVE,3,"rty"));
            defaultbucketList.add(new bucketlist(4,"책 100권 읽기", "다양한 장르의 책 100권 읽기", LocalDateTime.now().minusDays(203),12, AccountStatus.ACTIVE,4,"fgh"));
            defaultbucketList.add(new bucketlist(5,"새로운 언어 배우기", "영어, 중국어, 스페인어 중 한 언어 배우기", LocalDateTime.now().minusDays(109),120, AccountStatus.ACTIVE,5,"bnm"));
            
            saveBuckets(defaultbucketList);
        }
        
        loadBuckets();
    }
    /* 설명. ArrayList<bucketlist>를 받으면 파일로 컬렉션에 담긴 회원들을 출력하는 메소드() */
    private void saveBuckets(ArrayList<bucketlist> bucketLists) {
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));

            for(bucketlist bb: bucketLists) {
                oos.writeObject(bb);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if(oos != null) oos.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void loadBuckets() {
        try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)))) {
            while(true) {
                bucket.add((bucketlist) ois.readObject());
            }
        } catch (EOFException e) {
            System.out.println("버킷리스트 정보 읽어오기 완료!");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /* 설명. 버킷리스트 조회(활동여부에 따라)*/
    public ArrayList<bucketlist> findAllBuckets() {
        ArrayList<bucketlist> returnList = new ArrayList<>();
        for(bucketlist bb: bucket) {
            if (bb.getAccountStatus() == AccountStatus.ACTIVE) returnList.add(bb);
        }
        return returnList;
    }
    
    /* 설명. 버킷리스트 번호로 조회 */
    public bucketlist findBucketBy(int bucket_id) {
        for (bucketlist bb: bucket) {
            if (bb.getBucket_id() == bucket_id && bb.getAccountStatus() == AccountStatus.ACTIVE) {
                return bb;
            }
        }
        return null;
    }

    /* 설명. 버킷리스트 제목+내용으로 조회 */
    public List<bucketlist> findBucketByTitleOrContents(String title, String contents) {
        List<bucketlist> matchingBuckets = new ArrayList<>();
        for (bucketlist bb : bucket) {
            boolean titleMatches = title.isEmpty() || bb.getBucket_title().contains(title);
            boolean contentsMatches = contents.isEmpty() || bb.getContents().contains(contents);

            if (titleMatches && contentsMatches && bb.getAccountStatus() == AccountStatus.ACTIVE) {
                matchingBuckets.add(bb);
            }
        }
        return matchingBuckets; // 일치하는 버킷리스트 목록 반환
    }


    public long findLastBucketNo() {
        return bucket.get(bucket.size() - 1).getBucket_id();
    }

    public int registBucket(bucketlist registBucket) {

        MyObjectOutput moo = null;
        int result = 0;
        try {
            moo = new MyObjectOutput(new BufferedOutputStream(new FileOutputStream(file, true)));
            moo.writeObject(registBucket);
            moo.flush();            // 내부적으로 Buffered를 쓰고 있으니 객체 출력 강제화

            /* 설명. 컬렉션에 담긴 기존 버킷리스트를 지우고 다시 파일의 정보를 토대로 컬렉션이 버킷리스트로 채워지도록 작성 */
            bucket.clear();
            loadBuckets();

            result = 1;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if(moo != null) moo.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return result;
    }

    public int modifyBucket(bucketlist reformedBucket) {

        /* 설명. 1. repository가 가진 컬렉션의 버킷리스트부터 수정 */
        for (int i = 0; i < bucket.size(); i++) {
            if (bucket.get(i).getBucket_id() == reformedBucket.getBucket_id()) {
                bucket.set(i, reformedBucket);      // i를 reformedBucket의 값으로 교체한다.
                saveBuckets(bucket);                // 교체 할 버킷리스트가 포함된 전체 버킷리스트로 파일을 다시 덮어씌움
                return 1;
            }
        }
        return 0;
    }

    public  int removeBucket(long bucket_id){
        int result = 0;

        for (bucketlist bucketl: bucket){
            if(bucketl.getBucket_id() == bucket_id){
                bucketl.setAccountStatus(AccountStatus.DEACTIVE);

                saveBuckets(bucket);

                result = 1;
                return result;
            }
        }
        return result;
    }


}
