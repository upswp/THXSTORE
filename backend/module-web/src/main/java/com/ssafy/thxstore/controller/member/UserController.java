package com.ssafy.thxstore.controller.member;

import com.ssafy.thxstore.common.exceptions.ErrorCode;
import com.ssafy.thxstore.controller.common.ErrorsResource;
import com.ssafy.thxstore.controller.config.AppProperties;
import com.ssafy.thxstore.controller.member.Resource.MemberResource;
import com.ssafy.thxstore.member.domain.Member;
import com.ssafy.thxstore.member.dto.request.ModifyPatchMemberRequest;
import com.ssafy.thxstore.member.service.UserService;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RequestMapping(value = "/user/", produces = MediaTypes.HAL_JSON_VALUE)
public class UserController {

    private final UserService userService;

    private final AppProperties appProperties;

    @PatchMapping
    public ResponseEntity modifyUser(@RequestHeader String authorization, @RequestBody @Valid ModifyPatchMemberRequest modifyPatchMemberRequest) {
        String email = jwtToEmail(authorization);
        MemberResource memberResource;
        try {
            Member patchMember = userService.patchMember(email,modifyPatchMemberRequest);
            memberResource = new MemberResource(patchMember);
            memberResource.add(linkTo(UserController.class).withRel("patch-member"));
            memberResource.add(Link.of("/api/docs/index.html#resources-patch-member").withRel("profile"));
        } catch (IOException e) {
            return badRequest(ErrorCode.INVALID_INPUT_VALUE);
        }

        return ResponseEntity.ok(memberResource);
    }

    @DeleteMapping
    public ResponseEntity deleteUser(@RequestHeader String authorization) {
        String email = jwtToEmail(authorization);
        MemberResource memberResource;
        Member deleteMember = userService.deleteMember(email);
        memberResource = new MemberResource(deleteMember);
        memberResource.add(linkTo(UserController.class).withRel("delete-member"));
        memberResource.add(Link.of("/api/docs/index.html#resources-delete-member").withRel("profile"));
        return ResponseEntity.ok(memberResource);
    }

    public String jwtToEmail(String authorization){
        return Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(appProperties.getAuth().getTokenSecret()))
                .parseClaimsJws(authorization).getBody().getSubject();
    }

    private ResponseEntity badRequest(ErrorCode errors) {
        return ResponseEntity.badRequest().body(ErrorsResource.modelOf(errors));
    }
}
