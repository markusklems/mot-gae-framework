package de.eorganization.example.client.services;

import com.google.appengine.api.users.User;
import com.google.gwt.user.client.rpc.AsyncCallback;

import de.eorganization.example.client.model.LoginInfo;
import de.eorganization.example.client.model.Member;


public interface LoginServiceAsync {

	void login(String requestUri, AsyncCallback<LoginInfo> callback);

	void registerMember(Member member, AsyncCallback<Member> callback);

	void updateMember(Member member, AsyncCallback<Member> callback);

	void updateMember(User user, AsyncCallback<Member> callback);

}
