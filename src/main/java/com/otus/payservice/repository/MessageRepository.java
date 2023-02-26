package com.otus.payservice.repository;

import com.otus.payservice.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, String> {
}
