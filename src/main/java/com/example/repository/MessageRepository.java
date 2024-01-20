package com.example.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    
    @Query(value = "SELECT * FROM message WHERE message_text = :message_text AND posted_by = :posted_by AND time_posted_epoch = :time_posted_epoch", nativeQuery = true)
    Message findPostedbyExistingUser (@Param("message_text") String message_text, @Param("posted_by") int posted_by, @Param("time_posted_epoch") long time_posted_epoch);

    @Modifying
    @Query(value = "insert INTO message (message_text, posted_by, time_posted_epoch) VALUES (:message_text,:posted_by, :time_posted_epoch)", nativeQuery = true)
    @Transactional
    void savemessage(@Param("message_text") String message_text, @Param("posted_by") int posted_by, @Param("time_posted_epoch") long time_posted_epoch);

    @Query(value = "SELECT * FROM message", nativeQuery = true)
    List<Message> getallmessages();

    @Query( value = "SELECT * FROM message WHERE message_id = :message_id ", nativeQuery = true)
    Message getmessagesbyid(@Param("message_id") int message_id);

    @Modifying
    @Query (value = "DELETE FROM message WHERE message_id = :message_id ", nativeQuery = true)
    @Transactional
    void deletemessagebyid(@Param("message_id") int message_id);

    @Modifying (clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE Message m SET m.message_text =?1 WHERE message_id=?2")
    @Transactional
    void updatemessagebyid (@Param("message_text") String message_text, @Param("message_id") int message_id);
 
    @Query(value = "SELECT * FROM message WHERE posted_by = :posted_by", nativeQuery = true)
    List<Message> getMessagesByUser(@Param("posted_by") int posted_by);

}
