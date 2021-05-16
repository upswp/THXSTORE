package com.ssafy.thxstore.controller.store;

import io.openvidu.java.client.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.MediaTypes;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RequestMapping(value = "/session", produces = MediaTypes.HAL_JSON_VALUE)
public class SessionController {

    /* openVidu test */
    // private OpenVidu openVidu;
    //private OpenVi
    //private OpenVidu
    // SDK의 진입 점으로서의 OpenVidu 객체
    private OpenVidu openVidu;
    // Collection to pair session names and OpenVidu Session objects
    // 세션 이름과 OpenVidu 세션 개체를 페어링하기위한 컬렉션
    private Map<String, Session> mapSessions = new ConcurrentHashMap<>();
    // Collection to pair session names and tokens (the inner Map pairs tokens and role associated)
    // 세션 이름과 토큰 쌍을 이루는 컬렉션 (내부 맵 쌍 토큰 및 역할 관련)
    private Map<String, Map<String, OpenViduRole>> mapSessionNamesTokens = new ConcurrentHashMap<>();
    // URL where our OpenVidu server is listening
    // OpenVidu 서버가 수신하는 URL
    private String OPENVIDU_URL;
    // Secret shared with our OpenVidu server
    // OpenVidu 서버와 공유되는 비밀 docker 열 때, MY_SECRET 이걸로 함.
    private String SECRET;


    public SessionController(@Value("${openvidu.secret}") String secret, @Value("${openvidu.url}") String openviduUrl) {
        this.SECRET = secret;
        this.OPENVIDU_URL = openviduUrl;
        this.openVidu = new OpenVidu(OPENVIDU_URL, SECRET);
    }

    @RequestMapping(value = "/session", method = RequestMethod.POST)
    public String joinSession(@RequestParam(name = "data") String clientData,
                              @RequestParam(name = "session-name") String sessionName, Model model, HttpSession httpSession) {
//clientData = name, sessionName 세션 네임
//        try {
//            checkUserLogged(httpSession);
//        } catch (Exception e) {
//            return "index";
//        }
        System.out.println("Getting sessionId and token | {sessionName}={" + sessionName + "}");

        // Role associated to this user 이 사용자와 관련된 역할
        //OpenViduRole role = LoginController.users.get(httpSession.getAttribute("loggedUser")).role;
        OpenViduRole role = null; // 후작업 필요 

        // Optional data to be passed to other users when this user connects to the
        // video-call. In this case, a JSON with the value we stored in the HttpSession
        // object on login
        //이 사용자가 화상 통화에 연결할 때 다른 사용자에게 전달할 선택적 데이터입니다. 이 경우 로그인시 HttpSession 객체에 저장 한 값이있는 JSON
        String serverData = "{\"serverData\": \"" + httpSession.getAttribute("loggedUser") + "\"}";

        // Build connectionProperties object with the serverData and the role
        //serverData 및 역할을 사용하여 connectionProperties 개체를 빌드합니다.
        ConnectionProperties connectionProperties = new ConnectionProperties.Builder().type(ConnectionType.WEBRTC)
                .role(role).data(serverData).build();

        if (this.mapSessions.get(sessionName) != null) {
            // Session already exists
            System.out.println("Existing session " + sessionName);
            try {

                // Generate a new token with the recently created connectionProperties
                String token = this.mapSessions.get(sessionName).createConnection(connectionProperties).getToken();

                // Update our collection storing the new token
                this.mapSessionNamesTokens.get(sessionName).put(token, role);

                // Add all the needed attributes to the template
                model.addAttribute("sessionName", sessionName);
                model.addAttribute("token", token);
                model.addAttribute("nickName", clientData);
                model.addAttribute("userName", httpSession.getAttribute("loggedUser"));

                // Return session.html template
                return "session";

            } catch (Exception e) {
                // If error just return dashboard.html template
                model.addAttribute("username", httpSession.getAttribute("loggedUser"));
                return "dashboard";
            }
        } else {
            // New session
            System.out.println("New session " + sessionName);
            try {

                // Create a new OpenVidu Session
                Session session = this.openVidu.createSession();
                // Generate a new token with the recently created connectionProperties
                String token = session.createConnection(connectionProperties).getToken();

                // Store the session and the token in our collections
                this.mapSessions.put(sessionName, session);
                this.mapSessionNamesTokens.put(sessionName, new ConcurrentHashMap<>());
                this.mapSessionNamesTokens.get(sessionName).put(token, role);

                // Add all the needed attributes to the template
                model.addAttribute("sessionName", sessionName);
                model.addAttribute("token", token);
                model.addAttribute("nickName", clientData);
                model.addAttribute("userName", httpSession.getAttribute("loggedUser"));

                // Return session.html template
                return "session";

            } catch (Exception e) {
                // If error just return dashboard.html template
                model.addAttribute("username", httpSession.getAttribute("loggedUser"));
                return "dashboard";
            }
        }
    }

    @RequestMapping(value = "/leave-session", method = RequestMethod.POST)
    public String removeUser(@RequestParam(name = "session-name") String sessionName,
                             @RequestParam(name = "token") String token, Model model, HttpSession httpSession) throws Exception {

        try {
            checkUserLogged(httpSession);
        } catch (Exception e) {
            return "index";
        }
        System.out.println("Removing user | sessioName=" + sessionName + ", token=" + token);

        // If the session exists ("TUTORIAL" in this case)
        if (this.mapSessions.get(sessionName) != null && this.mapSessionNamesTokens.get(sessionName) != null) {

            // If the token exists
            if (this.mapSessionNamesTokens.get(sessionName).remove(token) != null) {
                // User left the session
                if (this.mapSessionNamesTokens.get(sessionName).isEmpty()) {
                    // Last user left: session must be removed
                    this.mapSessions.remove(sessionName);
                }
                return "redirect:/dashboard";

            } else {
                // The TOKEN wasn't valid
                System.out.println("Problems in the app server: the TOKEN wasn't valid");
                return "redirect:/dashboard";
            }

        } else {
            // The SESSION does not exist
            System.out.println("Problems in the app server: the SESSION does not exist");
            return "redirect:/dashboard";
        }
    }

    private void checkUserLogged(HttpSession httpSession) throws Exception {
        if (httpSession == null || httpSession.getAttribute("loggedUser") == null) {
            throw new Exception("User not logged");
        }
    }
}
