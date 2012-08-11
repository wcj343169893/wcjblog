package com.google.choujone.blog.util;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.google.choujone.blog.dao.UserDao;
import com.google.choujone.blog.entity.User;

public class Mail {
	public static void send(String title, String content) {
		// mail.send_mail();
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		String msgBody = content;
		UserDao ud = new UserDao();
		User blogUser = ud.getUserDetail();
		try {
			Message msg = new MimeMessage(session);
			msg
					.setFrom(new InternetAddress("wcj343169893@163.com",
							"choujone"));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
					blogUser.getEmail(), "administrator"));
			Multipart mp = new MimeMultipart();
			MimeBodyPart htmlPart = new MimeBodyPart();
			htmlPart.setContent(msgBody, "text/html");
			mp.addBodyPart(htmlPart);
			// System.out.println(Charset.defaultCharset());
			// subject = new String(subject.getBytes(),
			// Charset.defaultCharset());
			// subject=new Header(subject,"utf-8");
			// subject=Header(subject,Charset.defaultCharset());
			msg.setSubject(title);
			// msg.setText(msgBody);
			msg.setContent(mp);
			msg.setSentDate(Tools.changeTime(Tools.changeTime(new Date())));
			// msg.setReplyTo(addresses)
			Transport.send(msg);

		} catch (AddressException e) {
			// ...
			e.printStackTrace();
		} catch (MessagingException e) {
			// ...
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// 
			e.printStackTrace();
		}
	}

	public static void send(String bid, String content, String email) {
		// mail.send_mail();
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		String msgBody = content;
		String subject = "Blog " + bid + " reply";
		UserDao ud = new UserDao();
		User blogUser = ud.getUserDetail();
		try {
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(email, ""));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
					blogUser.getEmail(), "administrator"));
			if (bid == null || "-1".equals(bid.trim())) {
				subject = "Leave a message";
			}
			Multipart mp = new MimeMultipart();
			MimeBodyPart htmlPart = new MimeBodyPart();
			htmlPart.setContent(msgBody, "text/html");
			mp.addBodyPart(htmlPart);
			// System.out.println(Charset.defaultCharset());
			// subject = new String(subject.getBytes(),
			// Charset.defaultCharset());
			// subject=new Header(subject,"utf-8");
			// subject=Header(subject,Charset.defaultCharset());
			msg.setSubject(subject);
			// msg.setText(msgBody);
			msg.setContent(mp);
			msg.setSentDate(Tools.changeTime(Tools.changeTime(new Date())));
			// msg.setReplyTo(addresses)
			Transport.send(msg);

		} catch (AddressException e) {
			// ...
			e.printStackTrace();
		} catch (MessagingException e) {
			// ...
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// 
			e.printStackTrace();
		}
	}
}
