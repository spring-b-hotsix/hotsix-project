package com.sparta.hotsixproject.attachment.repository;

import com.sparta.hotsixproject.attachment.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
    List<Attachment> findByCard_Id(Long id);

    @Override
    Optional<Attachment> findById(Long id);
}
