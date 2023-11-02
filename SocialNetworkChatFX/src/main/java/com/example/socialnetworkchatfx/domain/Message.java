package com.example.socialnetworkchatfx.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Message extends Entity<Integer>{

    private Integer idUser1;
    private Integer idUser2;
    private String text;
    private LocalDateTime time;

    public Message(Integer idUser1, Integer idUser2, String text) {
        this.idUser1 = idUser1;
        this.idUser2 = idUser2;
        this.text = text;
        this.time = LocalDateTime.now();
    }

    public Message(Integer id,Integer idUser1, Integer idUser2, String text,LocalDateTime dateTime) {
        super.setId(id);
        this.idUser1 = idUser1;
        this.idUser2 = idUser2;
        this.text = text;
        this.time = dateTime;
    }

    public Integer getIdUser1() {
        return idUser1;
    }

    public void setIdUser1(Integer idUser1) {
        this.idUser1 = idUser1;
    }

    public Integer getIdUser2() {
        return idUser2;
    }

    public void setIdUser2(Integer idUser2) {
        this.idUser2 = idUser2;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return idUser1.equals(message.idUser1) && idUser2.equals(message.idUser2) && text.equals(message.text) && time.equals(message.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUser1, idUser2, text, time);
    }
}
