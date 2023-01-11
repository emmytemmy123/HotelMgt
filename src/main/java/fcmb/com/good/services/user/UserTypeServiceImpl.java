package fcmb.com.good.services.user;

import fcmb.com.good.exception.RecordNotFoundException;
import fcmb.com.good.filter.JwtFilter;
import fcmb.com.good.mapper.Mapper;
import fcmb.com.good.model.dto.enums.AppStatus;
import fcmb.com.good.model.dto.request.userRequest.UserTypeRequest;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.userResponse.RoleResponse;
import fcmb.com.good.model.dto.response.userResponse.UserTypeResponse;
import fcmb.com.good.model.entity.user.UserType;
import fcmb.com.good.repo.user.UserTypeRepository;
import fcmb.com.good.utills.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserTypeServiceImpl implements UserTypeService {

    private final UserTypeRepository userTypeRepository;

    private final JwtFilter jwtFilter;


    @Override
    public ApiResponse<List<UserTypeResponse>> getListOfUserType(int page, int size) {
        List<UserType> typeList = userTypeRepository.findAll(PageRequest.of(page,size)).toList();
        if(typeList.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        return new ApiResponse<>(AppStatus.SUCCESS.label,
                HttpStatus.OK.value(),
                Mapper.convertList(typeList, UserTypeResponse.class));

    }

    @Override
    public ApiResponse<UserTypeResponse> addUserType(UserTypeRequest request) {
        if(jwtFilter.isAdmin()){
            UserType type = Mapper.convertObject(request,UserType.class);
            type=userTypeRepository.save(type);
            return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                    Mapper.convertObject(type, UserTypeResponse.class));
        }
        return new ApiResponse(AppStatus.FAILED.label, HttpStatus.EXPECTATION_FAILED.value(),
                "You are not Authorized");

    }


    @Override
    public ApiResponse<UserTypeResponse> getUserTypeById(UUID userTypeId) {
        Optional<UserType> type = userTypeRepository.findByUuid(userTypeId);
        if(type.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        UserType cm = type.get();
        return new ApiResponse<UserTypeResponse>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertObject(cm,UserTypeResponse.class));
    }

    private UserType validateUserType(UUID uuid){
        Optional<UserType> userType = userTypeRepository.findByUuid(uuid);
        if(userType.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);
        return userType.get();
    }

    @Override
    public ApiResponse<UserTypeResponse> updateUserType(UUID userTypeId, UserTypeRequest request) {
        UserType userType = validateUserType(userTypeId);
        userType.setType(request.getType());
        userType = userTypeRepository.save(userType);
        return new ApiResponse<UserTypeResponse>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertObject(userType,UserTypeResponse.class));    }

    @Override
    public ApiResponse<String> deleteUserType(UUID usertypeId) {
        UserType type = validateUserType(usertypeId);
        userTypeRepository.delete(type);
        return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Deleted successfully");    }
}
