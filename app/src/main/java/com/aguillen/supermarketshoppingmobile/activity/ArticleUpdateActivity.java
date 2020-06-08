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
import com.aguillen.supermarketshoppingmobile.util.Mapper;
import com.aguillen.supermarketshoppingmobile.validate.ValidateArticle;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticleUpdateActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etDescription;
    private Spinner sCategory;
    private Button btBack;
    private Button btSave;
    private Button btExit;
    private Button btSelectImage;
    private ImageView ivOk;
    private String encodedImage;
    Article article = new Article();
    private ArticleServiceImpl service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_update);

        etName = (EditText) findViewById(R.id.et_name);
        etDescription = (EditText) findViewById(R.id.et_description);
        sCategory = (Spinner) findViewById(R.id.s_category);
        btBack = (Button) findViewById(R.id.bt_back);
        btSave = (Button) findViewById(R.id.bt_save);
        btExit = (Button) findViewById(R.id.bt_exit);
        btSelectImage = (Button) findViewById(R.id.bt_select_image);
        ivOk = (ImageView) findViewById(R.id.iv_ok);
        service = new ArticleServiceImpl();

        Intent intent = getIntent();
        article = (Article) intent.getSerializableExtra("article");

        etName.setText(article.getName());
        etDescription.setText(article.getDescription());
        sCategory.setSelection(getCategoryPosition(article.getCategory()));

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
                article.setName(etName.getText().toString());
                article.setDescription(etDescription.getText().toString());
                article.setCategory(sCategory.getSelectedItem().toString());
                if(!encodedImage.isEmpty()) {
                    article.setImage(encodedImage);
                }
                if(ValidateArticle.validateArticle(article)) {
                    updateArticle(getApplicationContext(), Mapper.buildArticleDTO(article));
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ArticleUpdateActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                    builder.setTitle("Modificar Articulo");
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
                Intent i = new Intent(ArticleUpdateActivity.this, ArticlesListActivity.class);
                finish();
                startActivity(i);
            }
        });

        btExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { finish(); }
        });

    }

    private Integer getCategoryPosition(String category) {
        Integer categoryPosition = null;
        String[] categorysList = getResources().getStringArray(R.array.category_arrays);
        for(int i=0;i<categorysList.length;i++) {
            if(categorysList[i].equals(category)) {
                categoryPosition = i;
                break;
            }
        }
        return categoryPosition;
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

    private void updateArticle(Context context, ArticleDTO articleDTO) {

        Call<ArticleDTO> call = service.getArticleService().update(articleDTO);

        call.enqueue(new Callback<ArticleDTO>() {
            @Override
            public void onResponse(Call<ArticleDTO> call, Response<ArticleDTO> response) {
                Intent i = new Intent(ArticleUpdateActivity.this, ArticlesListActivity.class);
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
