package com.google.choujone.blog.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.google.choujone.blog.common.Operation;
import com.google.choujone.blog.dao.IpDao;
import com.google.choujone.blog.entity.Ip;
import com.google.choujone.blog.util.Tools;

public class IpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/html;charset=utf-8");
		resp.setCharacterEncoding("UTF-8");
		resp.setHeader("Cache-Control", "no-cache");
		PrintWriter out = resp.getWriter();
		String operation = req.getParameter("opera") != null ? req
				.getParameter("opera") : "";
		String id = req.getParameter("id") != null ? req.getParameter("id")
				: "";
		String address = req.getParameter("address") != null ? req
				.getParameter("address") : "";
		IpDao ipdao = new IpDao();
		boolean flag = false;
		if (Operation.add.toString().equals(operation.trim())) {// 新增
			Ip ip = new Ip();
			// ip.setAddress(Tools.getIpAddr(req));
			ip.setAddress(address);
			flag = ipdao.operationIp(Operation.add, ip);
			out.println(flag);
		} else if (Operation.delete.toString().equals(operation.trim())) {// 删除
			flag = ipdao.deleteIp(Tools.strTolong(id));
			out.println(flag);
		} else {// 查询列表
			resp.setContentType("text/json;charset=utf-8");
			Map<Long, String> map = new HashMap<Long, String>();
			List<Ip> ips = ipdao.getIpList();
			for (Ip ip : ips) {
				map.put(ip.getId(), ip.getAddress());
			}
			String json = JSONObject.toJSONString(map);
			out.println(json);
		}
		out.close();
	}
}
