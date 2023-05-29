package med.voll.api.infra.security;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.api.domain.user.RepositoryUser;

@Component
public class SecurityFilter extends OncePerRequestFilter {
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private RepositoryUser repositoryUser;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain ) throws IOException, ServletException {		
		var authHeader = request.getHeader("Authorization"); 
		System.out.println(authHeader);
		
		if(authHeader != null ) {
			System.out.println("Token is not null");
			var token = authHeader.replace("Bearer ", "");
			System.out.println(token);
			System.out.println(tokenService.getSubject(token));
			var userName = tokenService.getSubject(token);
			if(userName != null) {
				System.out.println("Valid Token");
				var userLogin = repositoryUser.findByUsername(userName);
				var authentication = new UsernamePasswordAuthenticationToken(userLogin, null, userLogin.getAuthorities());
				
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}
		filterChain.doFilter(request, response);	
	}}