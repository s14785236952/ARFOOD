package com.example.peter.arfood;
        import android.content.Context;
        import android.util.Log;
        import android.widget.Toast;

        import com.example.peter.arfood.fragment.ExploreFragment;
        import com.example.peter.arfood.models.Explore;
        import com.example.peter.arfood.services.APIService;

        import java.util.List;

        import retrofit2.Call;
        import retrofit2.Callback;
        import retrofit2.Response;
        import retrofit2.Retrofit;
        import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient{

    private static RestClient instance = null;

    private ResultReadyCallback callback;

    private static final String BASE_URL = "https://food-s14785236952.c9users.io/";
    private APIService service;
    List<Explore> explores = null;
    boolean success = false;


    public RestClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        service = retrofit.create(APIService.class);
    }

    public List<Explore> getExplores() {
        Call<List<Explore>> explorelist = service.explores();
        explorelist.enqueue(new Callback<List<Explore>>() {
            @Override
            public void onResponse(Call<List<Explore>> call, Response<List<Explore>> response) {
                if (response.isSuccessful()) {
                    explores = response.body();
                    callback.resultReady(explores);
                }
            }

            @Override
            public void onFailure(Call<List<Explore>> call, Throwable t) {
                Log.e("REST", t.getMessage());
            }

        });
        return explores;
    }

    public void setCallback(ResultReadyCallback callback) {
        this.callback = callback;
    }

    public boolean createExplore(final Context ctx, Explore explore) {
        Call<Explore> u = service.createExplore(explore);
        u.enqueue(new Callback<Explore>() {
            @Override
            public void onResponse(Call<Explore> call, Response<Explore> response) {
                success = response.isSuccessful();
                if(success) {
                    Toast.makeText(ctx, "Explore Created", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ctx, "Couldn't create explore", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Explore> call, Throwable t) {
                Log.w("REST", t.getMessage());
                Toast.makeText(ctx, "Couldn't create explore", Toast.LENGTH_SHORT).show();
            }
        });
        return success;
    }

    public static RestClient getInstance() {
        if(instance == null) {
            instance = new RestClient();
        }
        return instance;
    }

    public interface ResultReadyCallback {
        public void resultReady(List<Explore> explores);
    }

}