package ee.esutoniagodesu.web.rest;

import ee.esutoniagodesu.security.xauth.Token;
import ee.esutoniagodesu.security.xauth.TokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;

@RestController
@RequestMapping("/api")
public class UserXAuthTokenController {

    private static final Logger log = LoggerFactory.getLogger(UserXAuthTokenController.class);

    @Inject
    private TokenProvider tokenProvider;

    @Inject
    private AuthenticationManager authenticationManager;

    @Inject
    private UserDetailsService userDetailsService;

    @RequestMapping(value = "/authenticate",
        method = RequestMethod.POST)
    public Token authorize(@RequestParam String username, @RequestParam String password) {

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = this.authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails details = this.userDetailsService.loadUserByUsername(username);
        return tokenProvider.createToken(details);
    }

    @RequestMapping(value = "/idauthenticate",
        method = RequestMethod.POST)
    public Token idauthenticate(@RequestBody String cert) throws CertificateNotYetValidException, CertificateExpiredException {
        log.debug("idauthenticate: cert=" + cert);

        X509Certificate certificate = null;

        certificate.checkValidity();

        RememberMeAuthenticationToken token = new RememberMeAuthenticationToken("admin", null, null);
        Authentication authentication = this.authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);


        UserDetails details = this.userDetailsService.loadUserByUsername("admin");
        return tokenProvider.createToken(details);
    }
}
