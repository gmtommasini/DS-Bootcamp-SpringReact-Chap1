package com.gmtommasini.DSBootCamp.cap1task.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gmtommasini.DSBootCamp.cap1task.entities.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
