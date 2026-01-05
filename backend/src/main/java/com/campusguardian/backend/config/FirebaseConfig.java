package com.campusguardian.backend.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
public class FirebaseConfig {

    @PostConstruct
    public void initialize() {
        try {
            if (FirebaseApp.getApps().isEmpty()) {

                // ✅ Load from classpath (CORRECT WAY)
                InputStream serviceAccount =
                        getClass().getClassLoader()
                                .getResourceAsStream(
                                        "campusguardian-8620a-firebase-adminsdk-fbsvc-ecdd7e8c6b.json"
                                );

                if (serviceAccount == null) {
                    throw new RuntimeException("❌ Firebase JSON NOT FOUND in resources folder");
                }

                FirebaseOptions options = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .build();

                FirebaseApp.initializeApp(options);
                System.out.println("🔥 Firebase Connected Successfully");
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("❌ Firebase initialization failed");
        }
    }
}