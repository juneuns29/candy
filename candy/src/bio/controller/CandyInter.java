package bio.controller;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

public interface CandyInter {
	String exec(HttpServletRequest req, HttpServletResponse resp)
								throws ServletException, IOException;
}
