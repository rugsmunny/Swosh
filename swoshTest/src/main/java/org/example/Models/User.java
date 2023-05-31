package org.example.Models;

import java.time.LocalDateTime;

public class User {
    private int id;
    private LocalDateTime account_created;
    private String password;
    private String first_name;
    private String last_name;
    private Long signature_number;
    private Long ssn;

    private  boolean logged_in;

    public User(){}

    public User(Long ssn, String password) {
        this.ssn = ssn;
        this.password = password;

    }

    public User(String password, String first_name, String last_name, Long ssn, Long signature_number) {
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
        this.ssn = ssn;
        this.signature_number = signature_number;
    }

    public User(int id, LocalDateTime created, String password, String first_name, String last_name, Long ssn, Long signature_number, boolean logged_in) {
        this.id = id;
        this.account_created = created;
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
        this.ssn = ssn;
        this.signature_number = signature_number;
        this.logged_in = logged_in;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getAccount_created() {
        return account_created;
    }

    public String getPassword() {
        return password;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public Long getSsn() {
        return ssn;
    }

    public boolean getLogged_in() {
        return logged_in;
    }

    public Long getSignature_number() {
        return signature_number;
    }
}
