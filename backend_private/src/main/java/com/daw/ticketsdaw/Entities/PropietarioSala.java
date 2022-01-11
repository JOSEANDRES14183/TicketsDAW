package com.daw.ticketsdaw.Entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "propietario_sala")
public class PropietarioSala extends Usuario {}
