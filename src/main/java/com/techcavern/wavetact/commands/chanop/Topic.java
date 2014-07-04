/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techcavern.wavetact.commands.chanop;

/**
 * @author jztech101
 */
/**
 public class Topic extends Command {

 public Topic() {
 super("topic", 10, "Broken Command :C");
 }

 @Override public void onCommand(MessageEvent<?> event, String... args) throws Exception {

 List<String> t = Arrays.asList(event.getChannel().getTopic()
 .split(args[0]));

 if (args[1].equalsIgnoreCase("switchpart")) {
 event.getChannel().send()
 .message(event.getChannel().getTopic()
 .replaceFirst(t.get(Integer.parseInt(args[2])),
 t.get(Integer.parseInt(args[3]))));
 event.getChannel().send()
 .message(event.getChannel().getTopic()
 .replaceFirst(t.get(Integer.parseInt(args[3])),
 t.get(Integer.parseInt(args[2]))));
 } else if (args[1].equalsIgnoreCase("setpart")) {
 String[] s = ArrayUtils.remove(args, 0);
 s = ArrayUtils.remove(args, 0);
 s = ArrayUtils.remove(args, 0);

 String sj = StringUtils.join(s, ' ');
 event.getChannel().send()
 .message(event.getChannel().getTopic()
 .replaceFirst(t.get(Integer.parseInt(args[2])),
 sj));
 } else if (args[1].equalsIgnoreCase("addpart")) {
 String[] s = ArrayUtils.remove(args, 0);

 String sj = StringUtils.join(s, ' ');
 event.getChannel().send()
 .message(event.getChannel().getTopic()
 .replace(t.get(t.size()),
 t.get(t.size()) + " " + args[0] + " " + sj));
 } else if (args[1].equalsIgnoreCase("removepart")) {
 if (Integer.parseInt(args[2]) == 1) {
 event.getChannel().getTopic().replace(t.get(1) + args[0], "");
 } else {
 event.getChannel().getTopic().replace(args[0] + t.get(Integer.parseInt(args[2])), "");
 }
 } else if (args[1].equalsIgnoreCase("set")) {
 String[] s = ArrayUtils.remove(args, 0);
 s = ArrayUtils.remove(args, 0);
 String sj = StringUtils.join(s, ' ');
 event.getChannel().send().setTopic(sj);
 } else if (args[1].equalsIgnoreCase("replace")) {
 event.getChannel().getTopic().replace(args[2], args[3]);
 } else if (args[1].equalsIgnoreCase("remove")) {
 String[] s = ArrayUtils.remove(args, 0);
 s = ArrayUtils.remove(args, 0);
 String sj = StringUtils.join(s, ' ');
 event.getChannel().getTopic().replace(sj, "");
 }
 }

 }
 **/