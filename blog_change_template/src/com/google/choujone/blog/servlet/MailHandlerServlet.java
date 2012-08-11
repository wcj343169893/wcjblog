package com.google.choujone.blog.servlet;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.choujone.blog.util.Mail;

/**
 * choujone'blog<br>
 * 功能描述：处理接收邮件 2012-2-15
 */
public class MailHandlerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		// System.out.println("处理邮件啦");
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		try {
			MimeMessage message = new MimeMessage(session, req.getInputStream());
			// System.out.println(message.getFrom());
			Address[] address = message.getFrom();
			// Address[] address_to=message.getReplyTo();
			// for (int i = 0; i < address.length; i++) {
			// System.out.println("address:"+i+" "+address[i]);
			// }
			// for (int i = 0; i < address_to.length; i++) {
			// System.out.println("address_to:"+i+" "+address_to[i]);
			// }
			// System.out.println(message.getContentType());
			// System.out.println(message.getContent());
			Multipart multipart = (Multipart) message.getContent();
			// System.out.println(multipart.getCount());
			// System.out.println(multipart.getBodyPart(0).getContent());
			// System.out.println(multipart.getBodyPart(1).getContent());
			// System.out.println(multipart.getBodyPart(0).getHeader("Cc"));
			// System.out.println(multipart.getBodyPart(0).getHeader("Subject"));
			// BodyPart bodyPart= multipart.getBodyPart(0);
			// System.out.println(bodyPart.getHeader("Subject"));
			Mail.send("-1", "我已经收到你的邮件，重复一下你发的内容:"
					+ multipart.getBodyPart(0).getContent(), address[0] + "");
		} catch (MessagingException e) {

		}
	}
}
