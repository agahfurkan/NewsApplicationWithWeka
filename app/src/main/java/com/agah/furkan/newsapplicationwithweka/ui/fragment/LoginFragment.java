package com.agah.furkan.newsapplicationwithweka.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.agah.furkan.newsapplicationwithweka.GlobalCons;
import com.agah.furkan.newsapplicationwithweka.MainApplication;
import com.agah.furkan.newsapplicationwithweka.R;
import com.agah.furkan.newsapplicationwithweka.data.local.sharedpref.model.UserModel;
import com.agah.furkan.newsapplicationwithweka.data.web.ErrorMessage;
import com.agah.furkan.newsapplicationwithweka.ui.viewmodel.LoginFragmentVM;
import com.agah.furkan.newsapplicationwithweka.util.AlarmManagerHelper;
import com.agah.furkan.newsapplicationwithweka.util.FragmentHelperUtil;
import com.agah.furkan.newsapplicationwithweka.util.NetworkUtil;
import com.agah.furkan.newsapplicationwithweka.util.ToastUtil;
import com.agah.furkan.newsapplicationwithweka.util.UserManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class LoginFragment extends BaseFragment {
    private static final int RC_SIGN_IN = 9001;
    private AppCompatActivity activity;
    private LoginFragmentVM loginFragmentVM;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
        activity = (AppCompatActivity) getActivity();
        mAuth = FirebaseAuth.getInstance();
        loginFragmentVM = ViewModelProviders.of(this).get(LoginFragmentVM.class);
        loginFragmentVM.getRespond().observe(this, s -> {
            FirebaseUser user = mAuth.getCurrentUser();
            UserModel userModel = new UserModel();
            if (user != null) {
                userModel.setUsername(user.getDisplayName());
                userModel.setEmail(user.getEmail());
                userModel.setUId(user.getUid());
                userModel.setPhotoUrl(Objects.requireNonNull(user.getPhotoUrl()).toString());
            }
            UserManager.setUserInfo(activity, userModel);
            AlarmManagerHelper.setWekaModelUpdateAlarm(Objects.requireNonNull(getContext()), 2, 10);
            AlarmManagerHelper.setNotificationAlarm(getContext(), 14, 30);
            ((MainApplication) Objects.requireNonNull(getActivity()).getApplicationContext()).loadModel();
            FragmentHelperUtil.loadWithAnim(activity, CategorySelectionFragment.newInstance());

        });
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(GlobalCons.FIREBASE_CLIENT_ID)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(activity, gso);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View pageView = inflater.inflate(R.layout.login_fragment, container, false);
        ButterKnife.bind(this, pageView);
        getToolbar().hide();
        return pageView;
    }

    @OnClick({R.id.google_sign_in_button, R.id.login_without_sign})
    void onClick(View v) {
        if (v.getId() == R.id.google_sign_in_button) {
            if (!NetworkUtil.isNetworkConnected(Objects.requireNonNull(getContext()))) {
                ToastUtil.showToast(ErrorMessage.NO_CONNECTION, activity);
            } else {
                signIn();
            }
        } else if (v.getId() == R.id.login_without_sign) {
            UserModel anonymLogin = new UserModel();
            anonymLogin.setUsername("empty");
            anonymLogin.setEmail("empty");
            anonymLogin.setUId("empty");
            anonymLogin.setPhotoUrl("empty");
            UserManager.setUserInfo(activity, anonymLogin);
            FragmentHelperUtil.loadWithAnim(activity, NewsFragment.newInstance("general"));
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                loginFragmentVM.firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Timber.tag("timber").d(e);
            }
        }
    }


  /*  private void signOut() {
        mAuth.signOut();
        mGoogleSignInClient.signOut().addOnCompleteListener((Executor) this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    }
                });
    }*/

   /* private void revokeAccess() {
        mAuth.signOut();
        mGoogleSignInClient.revokeAccess().addOnCompleteListener((Executor) this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    }
                });
    }*/
}
