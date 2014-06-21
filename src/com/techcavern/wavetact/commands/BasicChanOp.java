/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands;

import com.techcavern.wavetact.utils.Command;
import com.techcavern.wavetact.utils.IRCUtils;
import org.pircbotx.UserLevel;
import org.pircbotx.hooks.events.MessageEvent;

public class BasicChanOp {
                
           public class kick extends Command {
            public kick(){
                        super("kick", 10);
              }
                
                @Override
		public void onCommand(MessageEvent<?> event, String... args) throws Exception{
                    if(event.getChannel().getUserLevels(event.getBot().getUserBot()).contains(UserLevel.OP) && event.getChannel().isOwner(IRCUtils.getUserByNick(event.getChannel(), args[1])) == false && event.getChannel().isSuperOp(IRCUtils.getUserByNick(event.getChannel(), args[1])) == false){
                             event.getChannel().send().kick(IRCUtils.getUserByNick(event.getChannel(), args[1]));
                            } else if(event.getChannel().getUserLevels(event.getBot().getUserBot()).contains(UserLevel.OWNER)){
                             event.getChannel().send().kick(IRCUtils.getUserByNick(event.getChannel(), args[1]));
                            } else {
                                event.getChannel().send().message("Error: I must be ownered in the channel to kick someone that is protected or ownered");
                            }
                        }         
                    
                }                 
                   public class mode extends Command{

                    public mode(){
                        super("mode", 10);
                   }
                       @Override
                       public void onCommand(MessageEvent<?> event, String... args) throws Exception{
                           	            	event.getChannel().send().setMode(args[1]);
                       }
                   }
                        
                   public class part extends Command{

                    public part(){
                        super("part", 5);
                   }
                       @Override
                       public void onCommand(MessageEvent<?> event, String... args) throws Exception{
                           	            	event.getChannel().send().part();
                       }
                   }
                    public class voice extends Command{

                    public voice(){
                        super("voice", 10);
                   }
                       @Override
                       public void onCommand(MessageEvent<?> event, String... args) throws Exception{
 if(args[1].startsWith("-"))
	            	event.getChannel().send().deVoice(IRCUtils.getUserByNick(event.getChannel(), args[1].replaceFirst("-","")));
                             else{
                               event.getChannel().send().voice(IRCUtils.getUserByNick(event.getChannel(), args[1]));

                             }                       }
                   }
            public class op extends Command{

                    public op(){
                        super("op", 10);
                   }
                       @Override
                       public void onCommand(MessageEvent<?> event, String... args) throws Exception{
                           if(args[1].startsWith("-")){
	            	event.getChannel().send().deOp(IRCUtils.getUserByNick(event.getChannel(), args[1].replaceFirst("-","")));
                           }else{
                               event.getChannel().send().op(IRCUtils.getUserByNick(event.getChannel(), args[1]));

                             }	
                       }
                   }
            public class owner extends Command{

                    public owner(){
                        super("owner", 15);
                   }
                       @Override
                       public void onCommand(MessageEvent<?> event, String... args) throws Exception{
                if(args[1].startsWith("-"))
	            	event.getChannel().send().deOwner(IRCUtils.getUserByNick(event.getChannel(), args[1].replaceFirst("-","")));
                             else{
                               event.getChannel().send().owner(IRCUtils.getUserByNick(event.getChannel(), args[1]));

                             }                       }
                   }
                 public class protect extends Command{

                    public protect(){
                        super("protect", 15);
                   }
                       @Override
                       public void onCommand(MessageEvent<?> event, String... args)  throws Exception{
                if(args[1].startsWith("-"))
	            	event.getChannel().send().deSuperOp(IRCUtils.getUserByNick(event.getChannel(), args[1].replaceFirst("-","")));
                             else{
                               event.getChannel().send().superOp(IRCUtils.getUserByNick(event.getChannel(), args[1]));

                             }                       }
                   }
                
                  
                       
                         
	} 
                

	
