package com.ssafy.thxstore.controller.member;

import com.ssafy.thxstore.common.exceptions.ErrorCode;
import com.ssafy.thxstore.controller.common.CurrentUser;
import com.ssafy.thxstore.controller.common.ErrorsResource;
import com.ssafy.thxstore.controller.config.AppProperties;
import com.ssafy.thxstore.member.domain.Member;
import com.ssafy.thxstore.member.dto.request.UserRequest;
import com.ssafy.thxstore.member.service.UserService;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.DatatypeConverter;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RequestMapping(value = "/user", produces = MediaTypes.HAL_JSON_VALUE)
public class UserController {

    private final UserService userService;
    private final AppProperties appProperties;

    @PatchMapping
    public ResponseEntity modifyUser(@RequestHeader String authorization,
                                     @RequestBody UserRequest userRequest,
                                     @CurrentUser Member currentUser) {
        String loginUserEmail = currentUser.getEmail();
        String tokenEmail = jwtToEmail(authorization);
        if(!loginUserEmail.equals(tokenEmail)){
            return badRequest(ErrorCode.UNAUTHORIZED_REDIRECT_URI);
        }

        return ResponseEntity.created(null).body(null);
    }

    public String jwtToEmail(String authorization){
        return Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(appProperties.getAuth().getTokenSecret()))
                .parseClaimsJws(authorization).getBody().getSubject();
    }
    private ResponseEntity badRequest(ErrorCode errors) {
        return ResponseEntity.badRequest().body(ErrorsResource.modelOf(errors));
    }
}
