package com.bucket.hch.app.run;

import com.bucket.hch.app.aggregate.AccountStatus;
import com.bucket.hch.app.aggregate.bucketlist;
import com.bucket.hch.app.service.BucketService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class Application {

    private static final BucketService bs = new BucketService();

    public static void main(String[] args){

        Scanner sc = new Scanner(System.in);
        while(true){
            System.out.println("===== 버킷리스트 관리 프로그램 =====");
            System.out.println("1. 모든 버킷리스트 정보 보기");
            System.out.println("2. 버킷리스트번호로 조회");
            System.out.println("3. 버킷리스트 제목+내용으로 조회");
            System.out.println("4. 버킷리스트 생성");
            System.out.println("5. 버킷리스트 정보 수정");
            System.out.println("6. 버킷리스트 삭제");
            System.out.println("9. 프로그램 종료");
            System.out.print("메뉴를 선택해 주세요: ");
            int input = sc.nextInt();
            sc.nextLine();

            /* 설명.
             *  조회: find
             *  추가: regist
             *  수정: modify
             *  삭제: remove
             * */
            switch(input) {
                case 1: bs.findAllBuckets(); break;
                case 2:
                    bs.findBucketBy(chooseBucId());
                    break;
                case 3:
                    String[] titleContents = chooseTiCon();
                    List<bucketlist> matchingBuckets =
                    bs.findBucketByTitleOrContents(titleContents[0], titleContents[1]);
                    if (!matchingBuckets.isEmpty()) {
                        System.out.println("버킷리스트 조회 성공: ");
                        for (bucketlist b : matchingBuckets) {
                            System.out.println(b);
                        }
                    } else {
                        System.out.println("일치하는 버킷리스트가 없습니다.");
                    }
                    break;
                case 4:
//                    bucketlist selectBucketlist = bs.registMember(signup());
                    bs.registBucket(signup());
                    break;
                case 5:

                    /* 설명. 회원 조회(조회가 되었다고 가정) 수정 */
//                    bucketlist selectMember = bs.findMemberForMod(chooseMemNo());   // 기존 회원 사본 객체 반환 받기
                    modifyBucket();
                    /* 설명. 수정을 위해 입력된 내용을 바탕으로 회원 수정 */
//                    bs.modifyMember(reform(selectMember));      // 수정 된 내용을 담은 기존 회원 사본 객체 넘기기

                    break;
                case 6:
                    removeBucket();
                    break;
                case 9:
                    System.out.println("프로그램을 종료하겠습니다.");
                    return;
                default:
                    System.out.println("번호를 잘못 입력하셨습니다.");
            }
        }
    }
    private static int chooseBucId() {
        Scanner sc = new Scanner(System.in);
        System.out.print("해당 버킷리스트 번호를 입력하세요: ");
        return sc.nextInt();
    }

    private static String[] chooseTiCon() {
        Scanner sc = new Scanner(System.in);
        System.out.print("버킷리스트 제목을 입력하세요 (없으면 Enter): ");
        String title = sc.nextLine();
        System.out.print("버킷리스트 내용을 입력하세요 (없으면 Enter): ");
        String contents = sc.nextLine();
        return new String[] { title, contents }; // 제목과 내용을 배열로 반환
    }

    private static bucketlist signup() {
        bucketlist buck = null;

        Scanner sc = new Scanner(System.in);
//        System.out.print("아이디를 입력하세요: ");
//        String bucket_id = sc.nextLine();

        System.out.print("버킷리스트 제목을 입력하세요: ");
        String bucket_title = sc.nextLine();

        System.out.print("버킷리스트 내용을 입력하세요: ");
        String contents = sc.nextLine();

//        buck = new bucketlist(bucket_title);

        // 새로운 bucketlist 객체 생성
        buck = new bucketlist();
        buck.setBucket_title(bucket_title);
        buck.setContents(contents);
        buck.setCreatedAt(LocalDateTime.now()); // 현재 시간 설정
        buck.setAccountStatus(AccountStatus.ACTIVE); // 기본 활성 상태 설정

        // 추가 필드 설정이 필요할 경우 여기에 추가

        return buck; // 생성된 bucketlist 객체 반환

        }

    private static void modifyBucket() {
        int bucketId = chooseBucId();
        bucketlist existingBucket = bs.findBucketBy(bucketId);
        if (existingBucket != null) {
            System.out.println("현재 제목: " + existingBucket.getBucket_title());
            System.out.println("현재 내용: " + existingBucket.getContents());

            System.out.print("새 제목을 입력하세요 (없으면 Enter): ");
            String newTitle = new Scanner(System.in).nextLine();
            System.out.print("새 내용을 입력하세요 (없으면 Enter): ");
            String newContents = new Scanner(System.in).nextLine();

            if (!newTitle.isEmpty()) {
                existingBucket.setBucket_title(newTitle);
            }
            if (!newContents.isEmpty()) {
                existingBucket.setContents(newContents);
            }

            bs.modifyBucket(existingBucket);
            System.out.println("버킷리스트가 수정되었습니다.");
        } else {
            System.out.println("해당 버킷리스트를 찾을 수 없습니다.");
        }
    }

    private static void removeBucket() {
        int bucketId = chooseBucId();
        bs.removeBucket(bucketId);
    }

    }

