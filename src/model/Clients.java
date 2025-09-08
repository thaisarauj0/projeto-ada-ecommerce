package model;

import java.util.UUID;

public class Clients {
    private static String id;
    private String cpf;
    private String name;
    private String telephone;
    private String adress;
    private String email;
    private String birthDate;

    public Clients(String id, String cpf, String name, String telephone, String adress, String email) {
        if(cpf == null || cpf.isEmpty()){
            throw new IllegalArgumentException("CPF is required");
        }
        this.id = id;
        this.cpf = cpf;
        this.name = name;
        this.telephone = telephone;
        this.adress = adress;
        this.email = email;
        this.birthDate = birthDate;
    }

    public Clients( String name, String telephone, String email, String address) {
    }

    //Getters
    public String getId() {return id;}
    public String getCpf() {return cpf;}
    public String getName() {return name;}
    public String getTelephone() {return telephone;}
    public String getAdress() {return adress;}
    public String getEmail() {return email;}
    public String getBirthDate() {return birthDate;}

}
