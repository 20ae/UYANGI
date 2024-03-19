package com.example.socks_android;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.amigold.fundapter.FunDapter;
import com.example.socks_android.Interfaces.Favorite;
import com.example.socks_android.ui.fragment.FavoriteFragment;
import com.example.socks_android.ui.fragment.HomeFragment;
import com.example.socks_android.ui.fragment.MyPageFragment;
import com.example.socks_android.ui.fragment.OrdersFragment;
import com.example.socks_android.ui.fragment.ShoppingBasketFragment2;
import com.example.socks_android.ui.fragment.ShoppingFragment;
import com.example.socks_android.ui.search.SearchFragment2;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class OrdersActivity extends AppCompatActivity {

    //프래그먼트 설정
    private HomeFragment HF;
    private ShoppingBasketFragment2 SBF;
    private ShoppingFragment SF;
    private MyPageFragment MF;
    private SearchFragment2 SearchF;
    private OrdersFragment OF;

    //검색 기능
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        //메뉴 바 설정
        settingSideNavBar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            MenuItem searchItem = menu.findItem(R.id.main_menu1);
            searchView = (SearchView) searchItem.getActionView();
            searchView.setQueryHint("양말 검색");
            searchView.setOnQueryTextListener(queryTextListener);

            SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            if(null!=searchManager ) {
                searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            }
            searchView.setIconifiedByDefault(true);
        }
        searchView.setOnQueryTextListener(queryTextListener);

        return true;
    }

    private SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            // 텍스트 입력 후 검색 버튼이 눌렸을 때의 이벤트
            SearchF = new SearchFragment2();
            Bundle bundle = new Bundle(1);
            bundle.putString("key",query);
            SearchF.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.main_content, SearchF).addToBackStack(null).commit();
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            // 검색 글 한자 한자 눌렸을 때의 이벤트
            return false;
        }
    };

    public void settingSideNavBar() {
        //프래그먼트 초기화
        HF = new HomeFragment();
        SBF = new ShoppingBasketFragment2();
        SF = new ShoppingFragment();
        MF = new MyPageFragment();
        OF = new OrdersFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, OF).addToBackStack(null).commit();

        // 툴바 생성
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // 사이드 메뉴를 오픈하기위한 아이콘 추가
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu_button);
        // 사이드 네브바 구현
        DrawerLayout drawLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        // 메인 메뉴 : 검색 구현
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                OrdersActivity.this,
                drawLayout,
                toolbar,
                R.string.open,
                R.string.closed
        );

        //하단 네비게이션 바
        BottomNavigationView bottom_menu = findViewById(R.id.nav_view_Home);
        bottom_menu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem nav_item) {
                switch(nav_item.getItemId()) {
                    case R.id.navigation_1: //홈
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, HF).addToBackStack(null).commit();
                        return true;
                    case R.id.navigation_2: //쇼핑
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, SF).addToBackStack(null).commit();
                        return true;
                    case R.id.navigation_3: //장바구니
                        //allScreenHide();
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, SBF).addToBackStack(null).commit();
                        return true;
                }
                return false;
            }
        });

        // 사이드 네브바 클릭 리스너
        drawLayout.addDrawerListener(actionBarDrawerToggle);
        // 사이드 네브바 아이템 클릭 이벤트 설정
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.nav_count){
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_content, MF).addToBackStack(null).commit();
                }else if(id == R.id.nav_sup){
                    Intent intent = new Intent(getApplicationContext(), UserPreferenceActivity.class);
                    startActivity(intent);
                }else if(id == R.id.nav_setting){
                    Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                    startActivity(intent);
                }else if(id == R.id.nav_logout){
                    logout();
                }
                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

    }

    /***
     *  -> 뒤로가기시, 사이드 네브바 닫는 기능
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        finish();
    }

    private void logout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(OrdersActivity.this);
        builder.setTitle("로그아웃")
                .setMessage("로그아웃 하시겠습니까?")
                .setPositiveButton("로그아웃", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(OrdersActivity.this, LoginActivity.class);
                        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP); // 로그인 페이지로 돌아가면 id, pw 그대로 남아있음
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // id, pw 남아있지 않음
                        startActivity(intent);
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();

    }

}
