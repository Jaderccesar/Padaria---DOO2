/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;


public class Client {

    private Integer id;
    private String name;
    private String cpf;
    private String phone;
    private Integer totalPoints;

    //Construtor padrão (sem parâmetros).
    public Client() {
    }

    //Construtor com parâmetros.
    //Permite instanciar um objeto Client já com os dados iniciais definidos.
    public Client(String name, String cpf, String phone, Integer totalPoints) {
        this.name = name;
        this.cpf = cpf;
        this.phone = phone;
        this.totalPoints = totalPoints;
    }

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(Integer totalPoints) {
        this.totalPoints = totalPoints;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cpf='" + cpf + '\'' +
                ", phone='" + phone + '\'' +
                ", totalPoints=" + totalPoints +
                '}';
    }
}