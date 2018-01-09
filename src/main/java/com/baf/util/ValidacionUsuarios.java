package com.baf.util;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

import com.baf.util.BAFMovilResources;

public class ValidacionUsuarios {
	private String domain;
	private String ldapHost;
	private String searchBase;
	private String userAdmin;
	private String passAdmin;
	private Map<String, Object> resultLDAP;

	public ValidacionUsuarios() {
		this.domain = getConfiguracion("ws.cnf.ldap.domain");
		this.ldapHost = "ldap://" + getConfiguracion("ws.cnf.ldap.Host");
		this.searchBase = getConfiguracion("ws.cnf.ldap.searchBase");
		this.userAdmin = getConfiguracion("ws.cnf.ldap.userAdmin");
		this.passAdmin = getConfiguracion("ws.cnf.ldap.passAdmin");
	}

	public ValidacionUsuarios(String domain, String host, String dn, String userAdmin, String passAdmin) {
		this.domain = domain;
		this.ldapHost = host;
		this.searchBase = dn;
		this.userAdmin = userAdmin;
		this.passAdmin = passAdmin;
	}

	// Invocación normal
	public boolean isUsuarioAutentico(String usuario) {
		boolean salida = true;
		if (authenticate(userAdmin, passAdmin, usuario) == null) {
			salida = false;
			System.out.println("No es autentico usuario LDAP:" + usuario);
		}
		return salida;
	}

	// Invocación normal
	public boolean isUsuarioAutentico(String usuario, String password) {
		boolean salida = true;
		this.resultLDAP = authenticate(usuario, password, usuario);
		if (this.resultLDAP == null) {
			salida = false;
			System.out.println("No es autentico usuario en LDAP:" + usuario + " password:" + password);
		}
		return salida;
	}

	// Invocación manual
	@SuppressWarnings("rawtypes")
	private Map<String, Object> authenticate(String userAdmin, String passAdmin, String usuarioAutenticar) {
		String returnedAtts[] = { "sn", "givenName", "mail", "description", "displayName",
				"physicalDeliveryOfficeName", "memberOf" };
		String searchFilter = "(&(objectClass=user)(sAMAccountName=" + usuarioAutenticar + "))";

		// Create the search controls
		SearchControls searchCtls = new SearchControls();
		searchCtls.setReturningAttributes(returnedAtts);

		// Specify the search scope
		searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);

		Hashtable<String, String> env = new Hashtable<String, String>();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, ldapHost);
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, userAdmin + "@" + domain);
		env.put(Context.SECURITY_CREDENTIALS, passAdmin);
		// env.put(Context.SECURITY_PROTOCOL, "ssl");
		LdapContext ctxGC = null;
		// boolean ldapUser = false;

		try {
			ctxGC = new InitialLdapContext(env, null);
			// Search objects in GC using filters
			NamingEnumeration answer = ctxGC.search(searchBase, searchFilter, searchCtls);
			while (answer.hasMoreElements()) {
				SearchResult sr = (SearchResult) answer.next();

				Attributes attrs = sr.getAttributes();
				Map<String, Object> amap = null;
				if (attrs != null) {
					amap = new HashMap<String, Object>();
					NamingEnumeration ne = attrs.getAll();
					while (ne.hasMore()) {
						Attribute attr = (Attribute) ne.next();
						amap.put(attr.getID(), attr.get());
						// ldapUser = true;
					}
					ne.close();
				}
				return amap;
			}
		} catch (AuthenticationException ex2) {
			// ex2.printStackTrace();
		} catch (NamingException ex) {
			// ex.printStackTrace();
		}

		return null;
	}

	protected String getConfiguracion(String recurso) {
		return BAFMovilResources.rbConfiguracion.getString(recurso);
	}

	public Map<String, Object> getResultLDAP() {
		return resultLDAP;
	}

	public void setResultLDAP(Map<String, Object> resultLDAP) {
		this.resultLDAP = resultLDAP;
	}
}
