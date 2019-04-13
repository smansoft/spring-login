/**
 * 
 */
package com.smansoft.sl.bl.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.smansoft.sl.bl.services.api.IUserService;
import com.smansoft.sl.bl.services.vo.UserVO;
import com.smansoft.tools.print.api.IPrintToolStr;
import com.smansoft.tools.print.impl.PrintToolStr;

/**
 * @author SMan
 *
 */
@Service(SpringLoginUserDetailsServiceImpl.DEF_BEAN_NAME)
public class SpringLoginUserDetailsServiceImpl implements UserDetailsService {

	/**
	 * 
	 */
	@SuppressWarnings("unused")
	private static final IPrintToolStr printToolStr = PrintToolStr
			.getPrintToolInstance(LoggerFactory.getLogger(SpringLoginUserDetailsServiceImpl.class));

	/**
	 * 
	 */
	public static final String DEF_BEAN_NAME = "userDetailsService";

	/**
	 * 
	 */
	@Autowired
	@Qualifier(IUserService.DEF_BEAN_NAME)
	private IUserService userServiceBean;
	

	@Autowired
	@Qualifier("transactionManager")
	private PlatformTransactionManager transactionManager;


	/**
	 * 
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = Throwable.class)
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetails userDetails = null;
		try {
			UserVO userVO = userServiceBean.getUserByUserLogin(username);
			List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<SimpleGrantedAuthority>();
			List<String> authorities = userVO.getAuthorities();
			for(String authority: authorities) {
				grantedAuthorities.add(new SimpleGrantedAuthority(authority));
			}
			userDetails = new User(userVO.getLogin(),userVO.getPassword(),userVO.getEnabled(),true,true,true,grantedAuthorities);
		}
		catch (Exception ex) {
			throw new UsernameNotFoundException("User: " + username + " hasn't been found", ex);
		}
		return userDetails;
	}

}
