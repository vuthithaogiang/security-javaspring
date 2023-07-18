package com.giang.security.common;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;


@MappedSuperclass
public abstract class BaseEntity implements Cloneable, Serializable {


    @CreationTimestamp
    private LocalDateTime createAt;


    @UpdateTimestamp
    private LocalDateTime updateAt;


    @Transient
    private String updateAtInString;

    public BaseEntity() {
        super();
    }


    public BaseEntity(LocalDateTime createAt, LocalDateTime updateAt){
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public LocalDateTime getCreateAt() {
        return this.createAt;
    }


    public String getUpdateAtInString(){
        return this.updateAt.getHour() + ":" + this.updateAt.getMinute();
    }

    public void setUpdateAt(LocalDateTime updateAt){
        this.updateAt = updateAt;
    }


    @Override
    public String toString() {
        return "BaseEntity{" +
                "createAt=" + createAt +
                ", updateAt=" + updateAt +
                '}';
    }
}
