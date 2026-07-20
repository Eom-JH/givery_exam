package com.recipe;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "recipes")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private String making_time;
    private String serves;
    private String ingredients;
    private String cost;

    private String created_at;
    private String updated_at;

    // レコードが作成される前に現在時刻をセット
    @PrePersist
    protected void onCreate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.created_at = LocalDateTime.now().format(formatter);
        this.updated_at = this.created_at;
    }

    // レコードが更新される前に現在時刻をセット
    @PreUpdate
    protected void onUpdate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.updated_at = LocalDateTime.now().format(formatter);
    }

    // Getter & Setter
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getMaking_time() { return making_time; }
    public void setMaking_time(String making_time) { this.making_time = making_time; }
    public String getServes() { return serves; }
    public void setServes(String serves) { this.serves = serves; }
    public String getIngredients() { return ingredients; }
    public void setIngredients(String ingredients) { this.ingredients = ingredients; }
    public String getCost() { return cost; }
    public void setCost(String cost) { this.cost = cost; }
    public String getCreated_at() { return created_at; }
    public String getUpdated_at() { return updated_at; }
}