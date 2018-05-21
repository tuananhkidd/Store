package com.kidd.store.presenter.account.register;

import android.content.Context;

import com.google.firebase.firestore.FirebaseFirestore;
import com.kidd.store.R;
import com.kidd.store.common.Base64UtilAccount;
import com.kidd.store.common.Constants;
import com.kidd.store.common.ResponseCode;
import com.kidd.store.models.body.CustomerRegisterBody;
import com.kidd.store.models.model_chat.UserChat;
import com.kidd.store.models.response.ResponseBody;
import com.kidd.store.services.ApiClient;
import com.kidd.store.services.retrofit.register.RegisterService;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class RegisterInteratorImpl implements RegisterInterator {
    private Context context;
    private CompositeDisposable compositeDisposable;

    public RegisterInteratorImpl(Context context) {
        this.context = context;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void register(String username, String password, CustomerRegisterBody body, OnRegisterCompleteListener listener) {
        //Gọi đến api đăng ký
        Observable<Response<ResponseBody<String>>> observable = ApiClient.getClient().create(RegisterService.class)
                .CustomerRegister(Base64UtilAccount.getBase64Account(username, password), body);
        Disposable disposable = observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> {
                            switch (response.code()) {
                                //api trả về các mã code tương ứng. Trong mỗi trương hợp sẽ có kêt quả khác nhau
                                case ResponseCode.OK: {
                                    //mã 200 .đăng ký thành công
                                    FirebaseFirestore.getInstance().collection(Constants.USERS_COLLECTION)
                                            .document(username)
                                            .set(new UserChat(username, body.getFullName(), "ABC"))
                                            .addOnSuccessListener(documentReference -> listener.onRegisterSuccess(response.body().getData()))
                                            .addOnFailureListener(e -> listener.onError(e.getMessage()));
                                    ;
                                    break;
                                }
                                case ResponseCode.CONFLICT: {
                                    //mã 409 tài khoản đã tồn tài
                                    listener.onAccountExist();
                                    break;
                                }
                                case ResponseCode.FORBIDDEN: {
                                    //mã 403 mật khẩu không dủ
                                    listener.onError(context.getString(R.string.password_must_be_more_than_six_letter));
                                    break;
                                }
                                default:{
                                    //mặc didnh
                                    listener.onError(context.getString(R.string.server_error));
                                }
                            }
                        },
                        error -> {
                            //lỗi server
                            listener.onError(context.getString(R.string.server_error));
                        }
                );

        compositeDisposable.add(disposable);
    }

    @Override
    public void onViewDestroy() {
        context = null;
        compositeDisposable.clear();
    }
}
