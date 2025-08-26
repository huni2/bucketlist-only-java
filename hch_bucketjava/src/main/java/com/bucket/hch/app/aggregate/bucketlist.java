package com.bucket.hch.app.aggregate;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;

public class bucketlist implements Serializable {
    private static final AtomicLong idCounter = new AtomicLong(0); // AtomicLong 사용
    private long bucket_id;
    private String bucket_title;
    private String contents;
    private LocalDateTime createdAt;
    private int views = 0;
    private int userNo;
    private String userId;
    private AccountStatus accountStatus;

    public bucketlist() {
        this.bucket_id = idCounter.incrementAndGet(); // 기본 생성자에서 ID 할당
        this.createdAt = LocalDateTime.now(); // 생성 시각 자동 설정
    }

    /* 설명. 버킷리스트 작성 시 사용자가 입력한 값을 지닌 bucketlist 객체가 되기 위한 생성자 */
    public bucketlist(long bucket_id, String bucket_title, String contents, LocalDateTime createdAt, int views, AccountStatus accountStatus, int userNo, String userId) {
//        this.bucket_id = bucket_id;
        this.bucket_id = idCounter.incrementAndGet(); // ID 자동 증가
        this.bucket_title = bucket_title;
        this.contents = contents;
//        this.createdAt = createdAt;
        this.createdAt = LocalDateTime.now(); // 생성 시각 자동 설정
        this.views = views;
        this.userNo = userNo; // auto_increment로 할당된 값
        this.userId = userId;
        this.accountStatus = accountStatus;
    }

    public long getBucket_id() {
        return bucket_id;
    }

    public void setBucket_id(long bucket_id) {
        this.bucket_id = bucket_id;
    }

    public String getBucket_title() {
        return bucket_title;
    }

    public void setBucket_title(String bucket_title) {
        this.bucket_title = bucket_title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getUserNo() {
        return userNo;
    }

    public void setUserNo(int userNo) {
        this.userNo = userNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    @Override
    public String toString() {
        return "bucketlist{" +
                "bucket_id=" + bucket_id +
                ", bucket_title='" + bucket_title + '\'' +
                ", contents='" + contents + '\'' +
                ", createdAt=" + createdAt +
                ", views=" + views +
                ", userNo=" + userNo +
                ", userId='" + userId + '\'' +
                ", accountStatus=" + accountStatus +
                '}';
    }
}



