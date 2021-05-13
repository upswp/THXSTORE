package com.ssafy.thxstore.controller.member;

import com.ssafy.thxstore.common.exceptions.ErrorCode;
import com.ssafy.thxstore.controller.common.ErrorsResource;
import com.ssafy.thxstore.controller.common.annotation.CurrentUser;
import com.ssafy.thxstore.controller.member.Resource.MemberResource;
import com.ssafy.thxstore.member.domain.Member;
import com.ssafy.thxstore.member.dto.request.ModifyPatchMemberRequest;
import com.ssafy.thxstore.member.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RequestMapping(value = "/user", produces = MediaTypes.HAL_JSON_VALUE)
public class UserController {

    private final UserService userService;

    @PatchMapping
    public ResponseEntity modifyUser(@RequestBody @Valid ModifyPatchMemberRequest modifyPatchMemberRequest,
                                     @CurrentUser Member currentUser) {
        MemberResource memberResource;
        try {
            Member patchMember = userService.patchMember(currentUser,modifyPatchMemberRequest);
            memberResource = new MemberResource(patchMember);
            memberResource.add(linkTo(UserController.class).withRel("patch-member"));
            memberResource.add(Link.of("/api/docs/index.html#resources-patch-member"));
        } catch (IOException e) {
            return badRequest(ErrorCode.INVALID_INPUT_VALUE);
        }

        return ResponseEntity.ok(memberResource);
    }

    private ResponseEntity badRequest(ErrorCode errors) {
        return ResponseEntity.badRequest().body(ErrorsResource.modelOf(errors));
    }
}
