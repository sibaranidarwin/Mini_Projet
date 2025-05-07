package com.example.mini_project.dto.approval;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class InvoiceDetailDTO {

    private String profession;
    private String salary;

    public InvoiceDetailDTO() {}

    // Constructor dengan semua argumen
    public InvoiceDetailDTO(Long id, String profession, String salary) {
        this.profession = profession;
        this.salary = salary;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }
}
