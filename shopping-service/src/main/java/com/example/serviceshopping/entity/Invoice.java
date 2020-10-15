package com.example.serviceshopping.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "tlb_invoices")
public class Invoice {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number_invoice")
    private String numberInvoice;

    private String description;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "create_at")
    @Temporal(TemporalType.DATE)
    private Date createAt;



    @Valid
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    //cascade si se crea la cabecera se crea all
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "invoice_id")
    private List<InvoiceItem> items;

    private String state;

    //Constructor de factura con items
    public Invoice(){
        items = new ArrayList<>();
    }

    //Antes de crearse en la base de dato se le setea una fecha
    @PrePersist
    public void prePersist() {
        this.createAt = new Date();
    }

}
