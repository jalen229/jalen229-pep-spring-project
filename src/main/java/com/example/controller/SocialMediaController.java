package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
//@CrossOrigin(origins = "http://localhost:8080")
@RestController
//@RequestMapping("http://localhost:8080")
public class SocialMediaController 
{
  @Autowired
  AccountService accountService;
  @Autowired
  MessageService messageService;

  @PostMapping("/register") 
  public ResponseEntity<Account> register(@RequestBody Account account)
  {
    try 
    {
      if(account.getUsername() == null || account.getUsername().length() == 0)
      {
        return new ResponseEntity<>(account, HttpStatus.BAD_REQUEST); 
      }
      if(account.getPassword().length() < 4 )
      {
        return new ResponseEntity<>(account, HttpStatus.BAD_REQUEST); 
      }
      if(accountService.findAccountbyusername(account.getUsername()) != null)
      {
        return new ResponseEntity<>(account, HttpStatus.CONFLICT); 
      }
      Account newaccount = accountService.saveaccount(account);
      return new ResponseEntity<>(newaccount, HttpStatus.OK);
    } 
    catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  @PostMapping("/login") 
  public ResponseEntity<Account> login(@RequestBody Account account)
  {
    try
    {
      Account newaccount = accountService.login(account.getUsername(),account.getPassword());
      if(newaccount == null)
      {
        return new ResponseEntity<Account>(newaccount, HttpStatus.UNAUTHORIZED);
      }
      else
      return new ResponseEntity<Account>(newaccount, HttpStatus.OK);
    }
    catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  //Messages
  @PostMapping("/messages") 
  public ResponseEntity<Message> messages(@RequestBody Message message)
  {
    try 
    {
      if(message.getMessage_text() == null || message.getMessage_text().length() > 255
      ||message.getMessage_text().length() == 0
      || accountService.FindAccountByAccountId(message.getPosted_by()) == null
      
     )
      {
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST); 
      }
      
      Message newmessage = messageService.savemessage(message);
      return new ResponseEntity<>(newmessage, HttpStatus.OK);
    } 
    catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  
  //@RequestMapping(method = RequestMethod.GET, params = {""})
  @GetMapping("/messages")
  public ResponseEntity<List<Message>> messages()
  {
    try
    {
      List<Message> messages = messageService.getallmessages();
      return new ResponseEntity<>(messages, HttpStatus.OK);
    } 
    catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  @GetMapping("/messages/{message_id}")
  public @ResponseBody Message displayInfo(@PathVariable int message_id)
  {
    try
    {
      Message messages = messageService.getmessagesbyid(message_id);
      return messages;
      //ResponseEntity.ok(messageList)
    } 
    catch (Exception e) 
    {
      return null;
    }
  }
  @DeleteMapping("/messages/{message_id}")
  public ResponseEntity<String> deleteResource(@PathVariable Integer message_id) 
  {
    try
    {
      // Your logic to delete the resource based on the id
      int deletedCount = messageService.deletemessagebyid(message_id);
      if (deletedCount > 0) 
      {
          // If resource is deleted successfully, return the count (integer) with HttpStatus.OK
          return new ResponseEntity<>("" + deletedCount + "", HttpStatus.OK);
      } 
      else 
      {
          // If resource with the given id is not found, return HttpStatus.NOT_FOUND
          return new ResponseEntity<>("", HttpStatus.OK);
      }
    }
    catch (Exception e) 
    {
      return null;
    }
  }
  @PatchMapping("messages/{message_id}")
    public ResponseEntity<String> updateMessage(
            @PathVariable("message_id") int messageId,
            @RequestBody Message updateRequest)
    {

        // Validate if the new message text is not blank and not over 255 characters
        String newMessageText = updateRequest.getMessage_text();
        if (newMessageText == null || newMessageText.isBlank() || newMessageText.length() > 255) {
            return ResponseEntity.badRequest().body("Invalid message text");
        }

        // Check if the message with given id exists
        if (!messageService.exists(messageId)) {
            return ResponseEntity.badRequest().body("Message not found");
        }

        try
        {
          boolean updateSuccessful = messageService.updatemessagebyid(messageId, newMessageText);

          if (updateSuccessful) {
              return ResponseEntity.ok("1");
          } else {
              return ResponseEntity.badRequest().body("Update failed for unknown reason");
          }
        }
        catch(Exception e)
        {
          return null;
        }
    }
    @GetMapping("accounts/{account_id}/messages")
    public ResponseEntity<List<Message>> getMessagesByUser(@PathVariable int account_id) {
        try {
            // Your logic to retrieve messages by user from the database
            List<Message> messages = messageService.getMessagesByUser(account_id);

            // Return the messages with HttpStatus.OK
            return new ResponseEntity<>(messages, HttpStatus.OK);
        } catch (Exception e) {
            // Handle exceptions appropriately (log or return a meaningful error response)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}