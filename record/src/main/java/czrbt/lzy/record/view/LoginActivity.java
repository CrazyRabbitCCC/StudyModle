package czrbt.lzy.record.view;

import android.accounts.Account;
import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentResolver;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import czrbt.lzy.record.R;
import czrbt.lzy.record.data.Provider;
import czrbt.lzy.record.data.UserColumns;
import czrbt.lzy.record.person.LoginPerson;
import czrbt.lzy.record.view.viewHelper.LoginHelper;

import static android.Manifest.permission.READ_CONTACTS;
/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity<LoginHelper,LoginPerson> implements LoaderCallbacks<Cursor>,LoginHelper {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;


    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mLoginFormView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }


    @Override
    protected void initView() {
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        populateAutoComplete();
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener((textView, id, keyEvent) -> {
            if (id == R.id.login || id == EditorInfo.IME_NULL) {
                attemptLogin();
                return true;
            }
            return false;
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(view -> attemptLogin());

        mLoginFormView = findViewById(R.id.login_form);
        ContentResolver.setSyncAutomatically(new Account("name","type"), Provider.AUTHORITY, true);
    }

    protected Activity getActivity() {
        return this;
    }

    @Override
    public LoginPerson getPerson() {
        return new LoginPerson(this,this);
    }

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }
        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, v -> requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS));
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (person == null) {
            return;
        }
        if (check()){
            person.login();
        }
    }


    public boolean check(){
        // Reset errors.
        mEmailView.setError(null);
       mPasswordView.setError(null);

        if (TextUtils.isEmpty(getAccount())) {
            mEmailView.setError(getString(R.string.error_field_required));
            mEmailView.requestFocus();
            return false;
        }
        if (!person.checkEmail()) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            mEmailView.requestFocus();
            return false;
        }
        if (!TextUtils.isEmpty(getPassword()) && !person.checkPassword()) {
           mPasswordView.setError(getString(R.string.error_invalid_password));
           mPasswordView.requestFocus();
            return false;
        }

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mPasswordView.getWindowToken(), 0);
        return true;

    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                Provider.Users.CONTENT_URI,
                new String[]{UserColumns.ACCOUNT,UserColumns.PASSWORD,UserColumns.IS_REMEMBER}
                ,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        if (cursor!=null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                emails.add(cursor.getString(cursor.getColumnIndex(UserColumns.ACCOUNT)));
                cursor.moveToNext();
            }
            addEmailsToAutoComplete(emails);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emails) {
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<>(LoginActivity.this, android.R.layout.simple_dropdown_item_1line, emails);
        mEmailView.setAdapter(adapter);
    }

    @Override
    public void refreshView() {

    }

    @Override
    public void addData(Object o) {

    }

    @Override
    public void postData(Object o) {

    }

    @Override
    public void afterLoading() {
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public SwipeRefreshLayout getRefreshLayout() {
        return null;
    }

    @Override
    public View getViewForSnack() {
        return null;
    }


    @Override
    public String getAccount() {
        return mEmailView.getText().toString();
    }

    @Override
    public String getPassword() {
        return mPasswordView.getText().toString();
    }
}

