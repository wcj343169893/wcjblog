package com.snssly.sms.servlet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.snssly.sms.commons.Config;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class ImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Font mFont = new Font("Times New Roman", Font.PLAIN, 18);
	private final int width = 60;
	private final int height = 20;

	// Initialize global variables
	/*
	 * (non-Javadoc) -- 图片验证
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#service(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("image/jpeg");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		// ImageIO.write(this.getImage(request), "JPEG",
		// response.getOutputStream());
		JPEGImageEncoder encode = JPEGCodec.createJPEGEncoder(response
				.getOutputStream());
		encode.encode(this.getImage(request));
	}

	// Process the HTTP Get request
	public BufferedImage getImage(HttpServletRequest request)
			throws ServletException, IOException {

		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);

		Graphics g = image.getGraphics();
		Random random = new Random();

		g.setColor(new Color(130, 130, 130));
		g.fillRect(0, 0, width, height);

		g.setFont(mFont);

		for (int i = 0; i < 150; i++) {
			g.setColor(getRandColor(80, 120));
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x, y, x + xl, y + yl);
		}

		String rand = this.getString();
		char c;
		for (int i = 0; i < 4; i++) {
			c = rand.charAt(i);
			g.setColor(new Color(20 + random.nextInt(80) + 110, 20 + random
					.nextInt(80) + 110, 20 + random.nextInt(80) + 110)); // 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
			g.drawString(String.valueOf(c), 13 * i + 6, 16);
		}

		HttpSession session = request.getSession();
		session.setAttribute(Config.IMG_CODE, rand.toLowerCase());
		g.dispose();
		return image;
	}

	private Color getRandColor(int fc, int bc) { // 给定范围获得随机颜色
		Random random = new Random();
		if (fc > 255) {
			fc = 255;
		}
		if (bc > 255) {
			bc = 255;
		}
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

	private String getString() {
		Random random = new Random(new Date().getTime());
		String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz";
		String s = null;
		int x = random.nextInt(str.length() - 1);
		s = str.substring(x, x + 1);
		x = random.nextInt(str.length() - 10) + 10;
		s += str.substring(x, x + 1);
		x = random.nextInt(str.length() - 15) + 10;
		s += str.substring(x, x + 1);
		x = random.nextInt(str.length() - 8) + 8;
		s += str.substring(x, x + 1);
		return s;
	}

}
