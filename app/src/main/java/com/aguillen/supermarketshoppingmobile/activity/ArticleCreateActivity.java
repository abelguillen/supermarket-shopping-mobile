package com.aguillen.supermarketshoppingmobile.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.aguillen.supermarketshoppingmobile.R;
import com.aguillen.supermarketshoppingmobile.dto.ArticleDTO;
import com.aguillen.supermarketshoppingmobile.model.Article;
import com.aguillen.supermarketshoppingmobile.service.article.ArticleServiceImpl;
import com.aguillen.supermarketshoppingmobile.util.Image;
import com.aguillen.supermarketshoppingmobile.util.Mapper;
import com.aguillen.supermarketshoppingmobile.validate.ValidateArticle;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticleCreateActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etDescription;
    private Spinner sCategory;
    private Button btBack;
    private Button btSave;
    private Button btExit;
    private Button btSelectImage;
    private ImageView ivOk;
    private String encodedImage;
    private ArticleServiceImpl service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_article);

        etName = (EditText) findViewById(R.id.et_name);
        etDescription = (EditText) findViewById(R.id.et_description);
        sCategory = (Spinner) findViewById(R.id.s_category);
        btBack = (Button) findViewById(R.id.bt_back);
        btSave = (Button) findViewById(R.id.bt_save);
        btExit = (Button) findViewById(R.id.bt_exit);
        btSelectImage = (Button) findViewById(R.id.bt_select_image);
        ivOk = (ImageView) findViewById(R.id.iv_ok);
        service = new ArticleServiceImpl();

        encodedImage = "";

        btSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();
            }
        });

        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString();
                String description = etDescription.getText().toString();
                String category = sCategory.getSelectedItem().toString();
                if(encodedImage.isEmpty()) encodedImage = Image.NO_IMAGE;
                Article article = new Article(name, description, category, encodedImage);
                if(ValidateArticle.validateArticle(article)) {
                    saveArticle(getApplicationContext(), Mapper.buildArticleDTO(article));
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ArticleCreateActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                    builder.setTitle("Nuevo Articulo");
                    builder.setMessage("El nombre del articulo no puede ser vacio.");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    dialog.getWindow().setBackgroundDrawableResource(android.R.color.background_light);
                    return;
                }
            }
        });

        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ArticleCreateActivity.this, ArticlesListActivity.class);
                finish();
                startActivity(i);
            }
        });

        btExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { finish(); }
        });
    }

    private void uploadImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent, "Seleccione la aplicacion"), 10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK) {
            Uri imageUri = data.getData();
            InputStream imageStream = null;
            try {
                imageStream = getContentResolver().openInputStream(imageUri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
            encodedImage = encodeImage(selectedImage);
            ivOk.setVisibility(View.VISIBLE);
        }
    }

    private String encodeImage(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);

        return encImage;
    }

    private void saveArticle(Context context, ArticleDTO articleDTO) {

        Call<ArticleDTO> call = service.getArticleService().create(articleDTO);

        call.enqueue(new Callback<ArticleDTO>() {
            @Override
            public void onResponse(Call<ArticleDTO> call, Response<ArticleDTO> response) {
                Intent i = new Intent(ArticleCreateActivity.this, ArticlesListActivity.class);
                finish();
                startActivity(i);
            }

            @Override
            public void onFailure(Call<ArticleDTO> call, Throwable t) {
                Log.e("Connection error: ", t.getMessage());
            }
        });
    }

}
