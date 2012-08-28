package de.eorganization.example.client.services;

import com.google.appengine.api.users.User;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.eorganization.example.client.model.LoginInfo;
import de.eorganization.example.client.model.Member;


@RemoteServiceRelativePath("loginService")
public interface LoginService extends RemoteService {

	public LoginInfo login(String requestUri);
	
	public Member registerMember(Member member);
	
	public Member updateMember(Member member);

	public Member updateMember(User user);

}
