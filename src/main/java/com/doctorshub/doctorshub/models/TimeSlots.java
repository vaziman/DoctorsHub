package com.doctorshub.doctorshub.models;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Table(name = "time_slots")
@Entity
public class TimeSlots {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "date")
    private String date;
    @Column(name = "time")
    private String time;
    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @OneToOne
    @JoinColumn(name = "Appointments_id", nullable = false)
    private Appointments appointments;

}
