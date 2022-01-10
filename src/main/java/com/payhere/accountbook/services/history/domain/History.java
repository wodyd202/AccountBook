package com.payhere.accountbook.services.history.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long money;
    private String memo;
    @Embedded
    private Writer writer;
    @CreatedDate
    private LocalDateTime createDateTime;

    private History(long money, String memo, Writer writer) {
        this.money = money;
        this.memo = memo;
        this.writer = writer;
    }

    public static History of(long money, String memo, Writer writer) {
        return new History(money, memo, writer);
    }

    public boolean updateMemo(Writer updater, String memo) {
        verifyHasUpdatePermission(updater);
        if(this.memo.equals(memo)){
            return false;
        }
        this.memo = memo;
        return true;
    }

    public boolean updateMoney(Writer updater, long money) {
        verifyHasUpdatePermission(updater);
        if(this.money == money){
            return false;
        }
        this.money = money;
        return true;
    }

    private void verifyHasUpdatePermission(Writer updater) {
        if(!writer.equals(updater)){
            throw new NoUpdatePermissionException();
        }
    }
}
