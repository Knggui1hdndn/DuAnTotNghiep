package com.knd.duantotnghiep.duantotnghiep.ui.details;

import static com.knd.duantotnghiep.duantotnghiep.utils.Utils.COUNT_SHOPPING_BAG;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.google.gson.Gson;
import com.knd.duantotnghiep.duantotnghiep.R;
import com.knd.duantotnghiep.duantotnghiep.core.BaseActivity;
import com.knd.duantotnghiep.duantotnghiep.databinding.ActivityDetailsBinding;
import com.knd.duantotnghiep.duantotnghiep.models.DetailOrderRequest;
import com.knd.duantotnghiep.duantotnghiep.models.DetailOrderResponse;
import com.knd.duantotnghiep.duantotnghiep.models.EvaluateRequest;
import com.knd.duantotnghiep.duantotnghiep.models.EvaluateResponse;
import com.knd.duantotnghiep.duantotnghiep.models.FeelingRequest;
import com.knd.duantotnghiep.duantotnghiep.models.ImageQuantity;
import com.knd.duantotnghiep.duantotnghiep.models.MessageResponse;
import com.knd.duantotnghiep.duantotnghiep.models.ProductResponse;
import com.knd.duantotnghiep.duantotnghiep.models.ProductDetail;
import com.knd.duantotnghiep.duantotnghiep.models.TypeFeeling;
import com.knd.duantotnghiep.duantotnghiep.ui.dialog.DialogRateCallback;
import com.knd.duantotnghiep.duantotnghiep.ui.dialog.DialogRateFragment;
import com.knd.duantotnghiep.duantotnghiep.ui.pay.OrderConfirmation;
import com.knd.duantotnghiep.duantotnghiep.utils.AdapterCallBack;
import com.knd.duantotnghiep.duantotnghiep.utils.ApiCallBack;
import com.knd.duantotnghiep.duantotnghiep.utils.FileProviderUtils;
import com.knd.duantotnghiep.duantotnghiep.utils.NetworkResult;
import com.knd.duantotnghiep.duantotnghiep.utils.UserPreferencesManager;
import com.knd.duantotnghiep.duantotnghiep.utils.Utils;
import com.squareup.picasso.Picasso;

import org.modelmapper.ModelMapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

@SuppressLint("NotifyDataSetChanged")
@AndroidEntryPoint
public class DetailsActivity extends BaseActivity<ActivityDetailsBinding> {
    private DetailsViewModel detailsViewModel;
    private ArrayList<ProductDetail> productQuantityDetails;
    private ArrayList<ImageQuantity> imgColorProducts;
    private ImageAdapter imageAdapter;
    private SizeAdapter sizeAdapter;
    private ColorAdapter colorAdapter;
    private EvaluateAdapter evaluateAdapter;
    @Inject
    public UserPreferencesManager userPreferencesManager;
    private String idProduct;
    private Boolean isFavourite = false;
    private Boolean isShowing = false;
    private final EvaluateRequest evaluateRequest = new EvaluateRequest();

    @Override
    public ActivityDetailsBinding getViewBinding() {
        return ActivityDetailsBinding.inflate(getLayoutInflater());
    }

    Animation animationExit;
    ProductResponse productResponse;
    ProductDetail productDetailClick;
    ImageQuantity imageQuantityClick;
    private int number;
    DetailOrderRequest detailOrderRequest;
    private ArrayList<MultipartBody.Part> part = new ArrayList<>();
    private final ArrayList<String> uris = new ArrayList<>();
    private ArrayList<EvaluateResponse> evaluates = new ArrayList<>();
    private ImageEvaluateAdapter evaluateAdapter1;
    private final ActivityResultLauncher<PickVisualMediaRequest> pickImage = registerForActivityResult(new ActivityResultContracts.PickMultipleVisualMedia(5), uris -> {
        if (!uris.isEmpty()) {
            uris.forEach(uri -> {
                part.add(createMultipartPart(FileProviderUtils.createFileFromUri(uri, this)));
                this.uris.add(String.valueOf(uri));
            });

            evaluateAdapter1.setData(this.uris);
        }
    });

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void initView() {
        setUpToolBar(binding.toolbar, true, getDrawable(R.drawable.baseline_arrow_back_ios_24));

        binding.rcyEvaluate.setAdapter(evaluateAdapter);
        binding.rcy.setAdapter(evaluateAdapter1);
        binding.include.shimmer.showShimmer(true);
        binding.rcyEvaluate.setAdapter(evaluateAdapter);
        binding.rcyColor.setAdapter(colorAdapter);
        binding.rcySize.setAdapter(sizeAdapter);
        binding.viewPager.setAdapter(imageAdapter);
        imageAdapter.setOnClickItemListener(this::handleItemImageClick);
        colorAdapter.setOnClickItemListener(this::handleColorAdapterClick);
        sizeAdapter.setOnClickItemListener(this::handleItemSizeClick);

        Animation animationEnter = AnimationUtils.loadAnimation(this, R.anim.transtion_add_to_bag);
        animationExit = AnimationUtils.loadAnimation(this, R.anim.transtion_add_to_bag_exit);

        DialogRateFragment dialogRateFragment = new DialogRateFragment(star -> {
            evaluateRequest.setStar(String.valueOf(star));
            detailsViewModel.addEvaluates(productResponse.get_id(), RequestBody.create(String.valueOf(star),
                            MediaType.parse("multipart/form-data")),
                    RequestBody.create(binding.edtEvaluate.getText().toString(), MediaType.parse("multipart/form-data")), part);
        });


        binding.edtEvaluate.setOnKeyListener((view, keyCode, keyEvent) -> {
            if (keyEvent.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                evaluateRequest.setComment(binding.edtEvaluate.getText().toString());
                dialogRateFragment.show(getSupportFragmentManager(), dialogRateFragment.getClass().getName());
                return true;
            }
            return false;
        });

        binding.tLayoutEvaluate.setEndIconOnClickListener(view ->
                pickImage.launch(new PickVisualMediaRequest.Builder()
                        .setMediaType(ActivityResultContracts.PickVisualMedia.ImageAndVideo.INSTANCE)
                        .build()));

        binding.buy.setOnClickListener(view -> {
            if (imageQuantityClick != null) {
                number = 0;
                updateItemQuantity(1);
                setInfoAddtoBag("Buy now", animationEnter);
                isShowing = true;
            } else {
                showMessage("Do not choose color");
            }

        });
        binding.include2.close.setOnClickListener(view -> {
            binding.include2.getRoot().startAnimation(animationExit);
            isShowing = false;
        });

        binding.addToCart.setOnClickListener(view -> {
            if (imageQuantityClick != null) {
                number = 0;
                updateItemQuantity(1);
                setInfoAddtoBag("Add to cart", animationEnter);
                isShowing = true;
            } else {
                showMessage("Do not choose color");
            }
        });

        binding.include2.txtAction.setOnClickListener(view -> {
            if (!binding.include2.txtAction.getText().toString().equals("Add to cart")) {
                detailsViewModel.checkBuyNow(imageQuantityClick.get_id(), Integer.parseInt(binding.include2.number.getText().toString()));
                return;
            }
            detailsViewModel.processDetailsOrder(detailOrderRequest);
        });

        binding.include2.plus.setOnClickListener(view -> {
            updateItemQuantity(1); // Increment the quantity by 1
        });

        binding.include2.minus.setOnClickListener(view -> {
            updateItemQuantity(-1); // Decrement the quantity by 1 if it's greater than 0
        });


        //     binding.viewPager.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                int position = getLastPositionItem(recyclerView);
//                updateItemImgColorClick(imgColorProducts.get(position));
//                colorAdapter.notifyDataSetChanged();
        //       }
        //   });
    }

    public MultipartBody.Part createMultipartPart(File file) {
        return MultipartBody.Part.createFormData("avatars", file.getName(), RequestBody.create(file, MediaType.parse("multipart/form-data")));
    }

    @Override
    protected int getMenu() {
        return R.menu.menu_details;
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        Utils.setUpBadge(this, binding.toolbar, COUNT_SHOPPING_BAG, R.id.cart);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.favorite) {
            if (!isFavourite) {
                detailsViewModel.addFavourite(idProduct);
            } else {
                detailsViewModel.deleteFavourite(idProduct);
            }
            isFavourite = !isFavourite;
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("SetTextI18n")
    private void setInfoAddtoBag(String txt, Animation animation) {
        binding.include2.getRoot().setVisibility(View.VISIBLE);
        binding.include2.txtTitle.setText(productResponse.getName() + "-" + productDetailClick.getSize() + "-" + imageQuantityClick.getImageProduct().getColor());
        binding.include2.txtAction.setText(txt);
        Picasso.get().load(imageQuantityClick.getImageProduct().getImage()).into(binding.include2.img);
        binding.include2.getRoot().startAnimation(animation);
        binding.include2.txtRemaining.setText(imageQuantityClick.getQuantity() + "");
        isShowing = true;
    }

    private void updateItemQuantity(int changeBy) {
        // Cập nhật newQuantity
        int newQuantity = number += changeBy;
        newQuantity = ensureValidQuantity(newQuantity);
        binding.include2.number.setText(String.valueOf(newQuantity));
        // Tạo detailOrderRequest
        detailOrderRequest = createDetailOrderRequest(newQuantity);
    }

    private int ensureValidQuantity(int newQuantity) {
        // Đảm bảo newQuantity nằm trong khoảng giá trị hợp lệ
        return Math.max(0, Math.min(newQuantity, imageQuantityClick.getQuantity()));
    }

    private DetailOrderRequest createDetailOrderRequest(int newQuantity) {
        // Tạo DetailOrderRequest với các thông số cần thiết

        return new DetailOrderRequest(
                productDetailClick.get_id(),
                productResponse.get_id(),
                imageQuantityClick.get_id(),
                newQuantity,
                productDetailClick.getSize(),
                productResponse.getSale(),
                productResponse.getPrice(),
                calculateTotalPrice(newQuantity)
        );
    }

    private float calculateTotalPrice(int newQuantity) {
        // Tính toán giá tiền dựa trên newQuantity và giá gốc của sản phẩm
        return newQuantity * productResponse.getPrice() - (newQuantity * productResponse.getPrice() * productResponse.getSale() / 100);
    }


    @Override
    protected void initData() {
        imageAdapter = new ImageAdapter();
//        SnapHelper snapHelper = new PagerSnapHelper();
//        RecyclerView.ItemDecoration itemDecoration = new RecyclerView.ItemDecoration() {
//            @Override
//            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
//                int parentWidth = parent.getWidth();
//                int childWidth = view.getWidth();
//                int margin = (parentWidth - childWidth) / 2;
//                outRect.left = margin;
//                outRect.right = margin;
//            }
//        };
//       binding.viewPager.addItemDecoration(itemDecoration);
//        snapHelper.attachToRecyclerView(binding.viewPager);
        evaluateAdapter = new EvaluateAdapter(new AdapterCallBack.EvaluateAdapterCallback() {
            @Override
            public void onYesClick(String idEvaluate) {
                detailsViewModel.handelFeelingEvaluates(idEvaluate, new FeelingRequest(TypeFeeling.LIKE));
            }

            @Override
            public void onNoClick(String idEvaluate) {
                detailsViewModel.handelFeelingEvaluates(idEvaluate, new FeelingRequest(TypeFeeling.DISLIKE));
            }
        });
        evaluateAdapter1 = new ImageEvaluateAdapter();
        colorAdapter = new ColorAdapter();
        sizeAdapter = new SizeAdapter();
        detailsViewModel = new ViewModelProvider(this).get(DetailsViewModel.class);
        idProduct = getIntent().getStringExtra("idProduct");
        detailsViewModel.getDetailsProduct(idProduct);
    }

    @Override
    protected void initObserver() {
        detailsViewModel.checkBuyNow.observe(this, detailOrderResponseNetworkResult -> ApiCallBack.handleResult(detailOrderResponseNetworkResult, new ApiCallBack.HandleResult<>() {
            @Override
            public void handleSuccess(MessageResponse datas) {
                int number = Integer.parseInt(binding.include2.number.getText().toString());

                double price = productResponse.getPrice();
                double sale = productResponse.getSale();
                double discountedPrice = price * (1 - (sale / 100));
                double totalPayment = discountedPrice * number;
                double totalCost =   price * (1 - (sale / 100)) * number;
                Intent intent = new Intent(DetailsActivity.this, OrderConfirmation.class);
                DetailOrderRequest orderRequest = new DetailOrderRequest(
                        productResponse.get_id(),
                        imageQuantityClick.get_id(),
                        number,
                        productDetailClick.getSize(),
                        productResponse.getSale(),
                        productResponse.getPrice(),
                        (float) (price * (1 - (sale / 100)) * number),
                        true
                        , productResponse.getName()
                );
                intent.putExtra("totalCost", price * number);
                intent.putExtra("discount", price * number-totalCost);
                intent.putExtra("totalPayment", totalPayment);
                intent.putExtra("detailsOrder", new Gson().toJson(orderRequest));
                startActivity(intent);
            }

            @Override
            public void handleError(String error) {
                showMessage(error);
            }

            @Override
            public void handleLoading() {

            }
        }));

        detailsViewModel.addEvaluate.observe(this, evaluateResponseNetworkResult -> {
            ApiCallBack.handleResult(evaluateResponseNetworkResult, new ApiCallBack.HandleResult<>() {
                @Override
                public void handleSuccess(EvaluateResponse data) {
                    uris.clear();
                    evaluateAdapter1.setData(uris);
                    binding.edtEvaluate.setText("");
                    evaluates.add(data);
                    evaluateAdapter.notifyDataSetChanged();
                    FileProviderUtils.deleteCache(DetailsActivity.this);
                }

                @Override
                public void handleError(String error) {

                }

                @Override
                public void handleLoading() {
                    // Handle loading if needed
                }
            });
        });
        detailsViewModel.handelFeelingEvaluates.observe(this, evaluateResponseNetworkResult -> {
            ApiCallBack.handleResult(evaluateResponseNetworkResult, new ApiCallBack.HandleResult<>() {
                @Override
                public void handleSuccess(EvaluateResponse data) {
                    EvaluateResponse dataOld = evaluates.stream()
                            .filter(evaluateResponse -> evaluateResponse.get_id().equals(data.get_id()))
                            .findFirst()
                            .orElse(null);
                    int index = evaluates.indexOf(dataOld);
                    showMessage(index + "");
                    EvaluateResponse existingEvaluateResponse = evaluates.get(index);
                    ModelMapper modelMapper = new ModelMapper();
                    modelMapper.map(data, existingEvaluateResponse);
                    evaluates.set(index, existingEvaluateResponse);
                    evaluateAdapter.notifyDataSetChanged();
                }

                @Override
                public void handleError(String error) {
                    // Handle error if needed
                    showMessage(error);
                }

                @Override
                public void handleLoading() {
                    // Handle loading if needed
                }
            });
        });


        detailsViewModel.deleteEvaluate.observe(this, evaluateResponseNetworkResult -> {
            ApiCallBack.handleResult(evaluateResponseNetworkResult, new ApiCallBack.HandleResult<>() {
                @Override
                public void handleSuccess(MessageResponse data) {
                    evaluates.removeIf(evaluateResponse -> evaluateResponse.getUser().get_id().equals(userPreferencesManager.getCurrentUser().get_id()));
                }

                @Override
                public void handleError(String error) {

                }

                @Override
                public void handleLoading() {

                }
            });
        });
        detailsViewModel.getEvaluates.observe(this, evaluateResponseNetworkResult -> {
            ApiCallBack.handleResult(evaluateResponseNetworkResult, new ApiCallBack.HandleResult<>() {
                @Override
                public void handleSuccess(List<EvaluateResponse> data) {
                    evaluates = new ArrayList<>();
                    evaluates.clear();
                    evaluates.addAll(data);
                    evaluateAdapter.setData(evaluates);
                }

                @Override
                public void handleError(String error) {
                    showMessage(error);
                }

                @Override
                public void handleLoading() {

                }
            });
        });
        detailsViewModel.processDetailsOrder.observe(this, result -> {
            ApiCallBack.handleResult(result, new ApiCallBack.HandleResult<>() {
                @Override
                public void handleSuccess(MessageResponse data) {
                    if (isShowing) {
                        binding.include2.getRoot().startAnimation(animationExit);
                        isShowing = false;
                        Utils.setUpBadge(DetailsActivity.this, binding.toolbar, COUNT_SHOPPING_BAG++, R.id.cart);
                    }
                }

                @Override
                public void handleError(String error) {
                    showMessage(error);
                }

                @Override
                public void handleLoading() {

                }
            });
        });


        detailsViewModel.getDetailsProduct.observe(this, productNetworkResult -> ApiCallBack.handleResult(productNetworkResult, new ApiCallBack.HandleResult<ProductResponse>() {
            @Override
            public void handleSuccess(ProductResponse data) {
                productResponse = data;
                isFavourite = data.getFavourite();
                productQuantityDetails = data.getProductDetails();

                binding.include.getRoot().setVisibility(View.GONE);
                binding.nestedScrollView.setVisibility(View.VISIBLE);

                menu.getItem(1).setIcon(isFavourite ? R.drawable.heart_click : R.drawable.heart_unclick);

                if (!productQuantityDetails.isEmpty()) {
                    imgColorProducts = productQuantityDetails.get(0).getImageProducts();
                    productQuantityDetails.get(0).setClick(true);
                    for (int i = 0; i < productQuantityDetails.size(); i++) {
                        try {
                            productQuantityDetails.get(i).getImageProducts().get(0).setClick(true);
                        } catch (Exception e) {
                        }
                    }
                    setInformationProduct(data);
                    imageAdapter.setData(imgColorProducts);
                    colorAdapter.setData(imgColorProducts);
                    sizeAdapter.setData(productQuantityDetails);
                    binding.viewPager.setCurrentItem(0);
                    detailsViewModel.getEvaluates(productResponse.get_id());
                }
            }

            @Override
            public void handleError(String error) {
                showMessage(error);
            }

            @Override
            public void handleLoading() {

            }
        }));
        detailsViewModel.addFavourite.observe(this, result -> ApiCallBack.handleResult(result, new ApiCallBack.HandleResult<MessageResponse>() {
            @Override
            public void handleSuccess(MessageResponse data) {
                menu.getItem(1).setIcon(R.drawable.heart_click);
            }

            @Override
            public void handleError(String error) {
                showMessage(error);
            }

            @Override
            public void handleLoading() {

            }
        }));

        detailsViewModel.deleteFavourite.observe(this, result -> ApiCallBack.handleResult(result, new ApiCallBack.HandleResult<MessageResponse>() {
            @Override
            public void handleSuccess(MessageResponse data) {
                menu.getItem(1).setIcon(R.drawable.heart_unclick);
            }

            @Override
            public void handleError(String error) {
                showMessage(error);

            }

            @Override
            public void handleLoading() {

            }
        }));


    }

    private int getLastPositionItem(RecyclerView recyclerView) {
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        if (linearLayoutManager != null) {
            return linearLayoutManager.findLastVisibleItemPosition();
        }
        return 0;
    }

    private void handleItemSizeClick(ProductDetail item) {
        if (isShowing) {
            binding.include2.getRoot().startAnimation(animationExit);
            isShowing = false;
        }

        updateItemSizeClick(item);
        setDataColorImg(item);
        productDetailClick = item;
        try {
            imageQuantityClick = item.getImageProducts().get(0);
        } catch (Exception ignored) {
            imageQuantityClick = null;
        }
    }

    private void handleItemImageClick(ImageQuantity item) {
        updateItemImgColorClick(item);
        colorAdapter.notifyDataSetChanged();
    }

    private void setDataColorImg(ProductDetail item) {
        imgColorProducts = item.getImageProducts();
        colorAdapter.setData(imgColorProducts);
        imageAdapter.setData(imgColorProducts);
    }

    @SuppressLint("SetTextI18n")
    private void updateItemSizeClick(ProductDetail item) {
        productQuantityDetails.replaceAll(productQuantityDetail -> {
            productQuantityDetail.setClick(false);
            return productQuantityDetail;
        });
        productQuantityDetails.get(productQuantityDetails.lastIndexOf(item)).setClick(true);
        if (imgColorProducts != null && !imgColorProducts.isEmpty()) {
            binding.include.txtRemaining.setText(imgColorProducts.get(0).getQuantity() + "");
        }
        sizeAdapter.notifyDataSetChanged();
    }

    @SuppressLint("SetTextI18n")
    private void handleColorAdapterClick(ImageQuantity item) {
        updateItemImgColorClick(item);
        colorAdapter.notifyDataSetChanged();
        imageQuantityClick = item;
        if (imgColorProducts.lastIndexOf(item) != -1)
            binding.viewPager.setCurrentItem(imgColorProducts.lastIndexOf(item), true);

        if (isShowing) {
            binding.include2.getRoot().startAnimation(animationExit);
            isShowing = false;
        }

    }

    private void updateItemImgColorClick(ImageQuantity item) {
        imgColorProducts.replaceAll(productQuantityDetail -> {
            productQuantityDetail.setClick(false);
            return productQuantityDetail;
        });
        if (imgColorProducts.lastIndexOf(item) != -1) {
            imgColorProducts.get(imgColorProducts.lastIndexOf(item)).setClick(true);
        }
    }

    @SuppressLint("SetTextI18n")
    private void setInformationProduct(ProductResponse data) {
        double price = data.getPrice();
        double sale = data.getSale();
        double discountedPrice = price * (1 - (sale / 100));
        String formattedPrice = Utils.formatPrice(price);
        String formattedDiscountedPrice = Utils.formatPrice(discountedPrice);

        if (sale == 0.0) {
            binding.txtSale.setVisibility(View.INVISIBLE);
            binding.txtPrice.setText("đ " + formattedPrice);
        } else {
            binding.txtPrice.setText("đ " + formattedDiscountedPrice);
            binding.txtSale.setText("đ " + formattedPrice);
        }

        binding.txtName.setText(data.getName());
        binding.txtDesrepstion.setText(data.getDescribe());
        binding.txtBought.setText(data.getSold() + "\n bought");

        int remainingQuantity = 0;

        if (data.getProductDetails() != null && !data.getProductDetails().isEmpty()) {
            ProductDetail productDetail = data.getProductDetails().get(0);
            productDetailClick = productDetail;
            if (productDetail.getImageProducts() != null && !productDetail.getImageProducts().isEmpty()) {
                imageQuantityClick = productDetail.getImageProducts().get(0);
                remainingQuantity = productDetail.getImageProducts().get(0).getQuantity();
            }
        }
    }
}