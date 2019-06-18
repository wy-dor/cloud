package com.learning.cloud.score.entity;

import java.util.Date;

public class ScoreRecord {
    private Long id;

    private Date recordTime;

    private Long scoreTypeId;

    private String userId;

    private Long scoreBeforeRecord;

    private Long score;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }

    public Long getScoreTypeId() {
        return scoreTypeId;
    }

    public void setScoreTypeId(Long scoreTypeId) {
        this.scoreTypeId = scoreTypeId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public Long getScoreBeforeRecord() {
        return scoreBeforeRecord;
    }

    public void setScoreBeforeRecord(Long scoreBeforeRecord) {
        this.scoreBeforeRecord = scoreBeforeRecord;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }
}