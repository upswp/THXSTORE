package com.ssafy.thxstore.controller.member;

import com.ssafy.thxstore.common.exceptions.ErrorCode;
import com.ssafy.thxstore.controller.common.ErrorsResource;
import com.ssafy.thxstore.controller.config.AppProperties;
import com.ssafy.thxstore.controller.member.Resource.MemberResource;
import com.ssafy.thxstore.controller.member.Resource.ModifyPatchMemberResource;
import com.ssafy.thxstore.controller.member.Resource.ModifyPutMemberResource;
import com.ssafy.thxstore.member.domain.Member;
import com.ssafy.thxstore.member.dto.response.ModifyPatchMemberResponse;
import com.ssafy.thxstore.member.dto.request.ModifyPatchMemberRequest;
import com.ssafy.thxstore.member.dto.request.ModifyPutMemberRequest;
import com.ssafy.thxstore.member.dto.response.ModifyPutMemberResponse;
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
        ModifyPatchMemberResource modifyPatchMemberResource;
        try {
            ModifyPatchMemberResponse patchMember = userService.patchMember(email, modifyPatchMemberRequest);
            modifyPatchMemberResource = new ModifyPatchMemberResource(patchMember);
            modifyPatchMemberResource.add(linkTo(UserController.class).withRel("patch-member"));
            modifyPatchMemberResource.add(Link.of("/api/docs/index.html#resources-patch-member").withRel("profile"));
        } catch (IOException e) {
            return badRequest(ErrorCode.INVALID_INPUT_VALUE);
        }

        return ResponseEntity.ok(modifyPatchMemberResource);
    }

    @PutMapping
    public ResponseEntity modifyUserLatAndLot(@RequestHeader String authorization, @RequestBody @Valid ModifyPutMemberRequest modifyPutMemberRequest) {
        String email = jwtToEmail(authorization);
        ModifyPutMemberResponse putMember= userService.putMember(email, modifyPutMemberRequest);
        ModifyPutMemberResource modifyPutMemberResponse = new ModifyPutMemberResource(putMember);
        modifyPutMemberResponse.add(linkTo(UserController.class).withRel("put-member"));
        modifyPutMemberResponse.add(Link.of("/api/docs/index.html#resources-put-member").withRel("profile"));
        return ResponseEntity.ok(modifyPutMemberResponse);
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

    public String jwtToEmail(String authorization) {
        return Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(appProperties.getAuth().getTokenSecret()))
                .parseClaimsJws(authorization).getBody().getSubject();
    }

    private ResponseEntity badRequest(ErrorCode errors) {
        return ResponseEntity.badRequest().body(ErrorsResource.modelOf(errors));
    }
}
