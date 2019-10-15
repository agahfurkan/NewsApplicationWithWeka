package com.agah.furkan.newsapplicationwithweka.ui.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Objects;


public class LoginFragmentVM extends ViewModel {
    private MutableLiveData<String> responseMutableLiveData;
    private FirebaseAuth mAuth;

    public LoginFragmentVM() {
        mAuth = FirebaseAuth.getInstance();
    }

    public MutableLiveData<String> getRespond() {
        if (responseMutableLiveData == null) {
            responseMutableLiveData = new MutableLiveData<>();
        }
        return responseMutableLiveData;
    }

    public void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        responseMutableLiveData.setValue(Objects.requireNonNull(mAuth.getCurrentUser()).getEmail());
                    } else {
                    }
                });
    }
}

