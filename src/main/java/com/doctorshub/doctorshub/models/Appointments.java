package com.doctorshub.doctorshub.models;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "appointments")
public class Appointments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "date")
    private String date;
    @Column(name = "time")
    private String time;
    @Column(name = "status")
    private String status;
    @Column(name = "doctor_id")
    private Long doctorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne
    @JoinColumn(name = "time_slots_id", nullable = false)
    private TimeSlots timeSlots;
}
