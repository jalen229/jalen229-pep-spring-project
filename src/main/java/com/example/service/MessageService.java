package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
public class MessageService 
{
    @Autowired  
    MessageRepository repo;
    public Message savemessage(Message messagetocreate)
    {
        repo.savemessage(messagetocreate.getMessage_text(), messagetocreate.getPosted_by(), messagetocreate.getTime_posted_epoch()); //messagetocreate.getPosted_by()
        Message message = repo.findPostedbyExistingUser(messagetocreate.getMessage_text(), messagetocreate.getPosted_by(), messagetocreate.getTime_posted_epoch());
        return message;
    }

    public List<Message> getallmessages()
    {
        return repo.getallmessages();
    }

    public Message getmessagesbyid(int message_id)
    {
        Message message = repo.getmessagesbyid(message_id);
        return message;
    }

    public Integer deletemessagebyid(Integer message_id)
    {
        Message messagetodelete = repo.getmessagesbyid(message_id);
        if(messagetodelete == null)
        {
            return 0;
        }
        else
        {
            repo.deletemessagebyid(message_id);
            return 1;
        }
        
    }
    
    public boolean updatemessagebyid(int message_id, String message_text)
    {
        repo.updatemessagebyid(message_text, message_id);
        //Message message = repo.getmessagesbyid(message_id);
        return this.exists(message_id);               
    }

    public boolean exists(Integer messageId) {
        Message exist = repo.getmessagesbyid(messageId);
        if(exist == null)
        {
            return false;
        }
        else
        {
            return true;
        }        
    }

    public List<Message> getMessagesByUser(int account_id) {
        return repo.getMessagesByUser(account_id);
    }
}
