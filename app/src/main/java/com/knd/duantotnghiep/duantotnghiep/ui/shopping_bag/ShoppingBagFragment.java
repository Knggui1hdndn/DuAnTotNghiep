package com.knd.duantotnghiep.duantotnghiep.ui.shopping_bag;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;

import com.knd.duantotnghiep.duantotnghiep.R;
import com.knd.duantotnghiep.duantotnghiep.core.BaseFragment;
import com.knd.duantotnghiep.duantotnghiep.databinding.FragmentShoppingBagBinding;
import com.knd.duantotnghiep.duantotnghiep.models.DetailOrderRequest;
import com.knd.duantotnghiep.duantotnghiep.models.DetailOrderResponse;
import com.knd.duantotnghiep.duantotnghiep.models.MessageResponse;
import com.knd.duantotnghiep.duantotnghiep.ui.pay.OrderConfirmation;
import com.knd.duantotnghiep.duantotnghiep.utils.AdapterCallBack;
import com.knd.duantotnghiep.duantotnghiep.utils.ApiCallBack;
import com.knd.duantotnghiep.duantotnghiep.utils.Utils;

import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ShoppingBagFragment extends BaseFragment<FragmentShoppingBagBinding> implements AdapterCallBack.OnClickViewDetailsOrder {
    private DetailsOrderAdapter detailsOrderAdapter;
    private ShoppingBagViewModel shoppingBagViewModel;
    private String idDetailOrder;
    private boolean isSelectedAll = false;
    private boolean isScoll = true;
    private boolean isClickPurchase = false;
    private final ArrayList<DetailOrderResponse> detailOrderResponses = new ArrayList<DetailOrderResponse>();
    private final ArrayList<DetailOrderResponse> detailOrderResponseClick = new ArrayList<DetailOrderResponse>();
    private DetailOrderResponse detailOrderUpdate;
    private double totalPayment;
    private double discount;
    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            detailOrderResponses.removeIf(detailOrderResponseClick::contains);
            detailsOrderAdapter.setData(detailOrderResponses);
            setInfoPurchased();
            requireActivity().unregisterReceiver(this);
        }
    };

    public ShoppingBagFragment() {
        super(R.layout.fragment_shopping_bag);
    }

    private boolean checkOneSelected = false;

    @Override
    public void initObserver() {
        shoppingBagViewModel.selectALl.observe(this, result -> {
            ApiCallBack.handleResult(result, new ApiCallBack.HandleResult<>() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void handleSuccess(MessageResponse unused) {
                    detailOrderResponses.replaceAll(detailOrderResponse -> {
                        detailOrderResponse.setSelected(isSelectedAll);
                        return detailOrderResponse;
                    });
                    setInfoPurchased();
                    detailsOrderAdapter.notifyDataSetChanged();
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
        shoppingBagViewModel.updateDetailOrders.observe(this, detailOrderResponseNetworkResult -> ApiCallBack.handleResult(detailOrderResponseNetworkResult, new ApiCallBack.HandleResult<>() {
            @Override
            public void handleSuccess(DetailOrderResponse data) {

                Optional<DetailOrderResponse> optionalOrderResponse = detailOrderResponses.stream()
                        .filter(detailOrderResponse -> Objects.equals(detailOrderResponse.get_id(), data.get_id()))
                        .findFirst();

                if (optionalOrderResponse.isPresent()) {
                    int index = detailOrderResponses.indexOf(optionalOrderResponse.get());
                    DetailOrderResponse existingDetailOrderResponse = detailOrderResponses.get(index);
                    ModelMapper modelMapper = new ModelMapper();
                    modelMapper.map(data, existingDetailOrderResponse);
                    detailOrderResponses.set(index, existingDetailOrderResponse);
                    detailsOrderAdapter.notifyItemChanged(index);
                }

                binding.purchase.setEnabled(true);
                setInfoPurchased();
            }

            @Override
            public void handleError(String error) {
                showMessage(error);

            }

            @Override
            public void handleLoading() {
                binding.purchase.setEnabled(false);

            }
        }));

        shoppingBagViewModel.getDetailOrders.observe(this, result -> ApiCallBack.handleResult(result, new ApiCallBack.HandleResult<>() {
            @SuppressLint("UnspecifiedRegisterReceiverFlag")
            @Override
            public void handleSuccess(List<DetailOrderResponse> data) {
                binding.checkBox.setChecked(checkSelectedAll(data));
                detailOrderResponses.clear();
                detailOrderResponses.addAll(data);
                detailsOrderAdapter.setData(detailOrderResponses);
                setInfoPurchased();
                isClickPurchase = false;

                if (!isScoll) {
                    Intent intent = new Intent(requireActivity(), OrderConfirmation.class);
                    intent.putExtra("order", data.get(0).getIdOrder());
                    intent.putExtra("totalCost", totalPayment + discount);
                    intent.putExtra("discount", discount);
                    intent.putExtra("totalPayment", totalPayment);
                    startActivity(intent);
                    detailOrderResponseClick.clear();
                    detailOrderResponses.forEach(detailOrderResponse -> {
                        if (detailOrderResponse.getSelected()) {
                            detailOrderResponseClick.add(detailOrderResponse);
                        }
                    });
                    checkOneSelected = true;
                    requireActivity().registerReceiver(broadcastReceiver,new IntentFilter("confirm"));
                }
            }

            @Override
            public void handleError(String error) {
                isClickPurchase = false;
                showMessage(error);
            }

            @Override
            public void handleLoading() {
                isClickPurchase = true;
            }
        }));
        shoppingBagViewModel.deleteDetailsOrder.observe(this, result -> ApiCallBack.handleResult(result, new ApiCallBack.HandleResult<>() {
            @Override
            public void handleSuccess(MessageResponse data) {
                DetailOrderResponse orderResponse = detailOrderResponses.stream()
                        .filter(detailOrderResponse -> Objects.equals(detailOrderResponse.get_id(), idDetailOrder))
                        .findFirst()
                        .orElse(null);  // Use orElse to avoid NoSuchElementException

                int index = detailOrderResponses.indexOf(orderResponse);
                if (index >= 0 && index < detailOrderResponses.size()) {
                    detailOrderResponses.remove(index);
                    detailsOrderAdapter.setData(detailOrderResponses);
                    setInfoPurchased();
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
    }

    private boolean checkSelectedAll(List<DetailOrderResponse> detailOrderResponse) {
        if (detailOrderResponse.isEmpty()) return false;
        int countTrue = 0;

        for (DetailOrderResponse item : detailOrderResponse) {
            if (item.getSelected()) {
                countTrue++;
            }
        }
        return countTrue == detailOrderResponse.size();
    }

    @SuppressLint("SetTextI18n")
    private void setInfoPurchased() {

        totalPayment = detailOrderResponses.stream().filter(DetailOrderResponse::getSelected).mapToDouble(DetailOrderResponse::getIntoMoney).sum();
        discount = detailOrderResponses.stream().filter(DetailOrderResponse::getSelected).mapToDouble(detailOrderResponse -> (detailOrderResponse.getSale() / 100.0) * detailOrderResponse.getPrice() * detailOrderResponse.getQuantity()).sum();

        binding.totalPayment.setText("đ " + Utils.formatPrice(totalPayment));
        binding.txtDiscount.setText("đ " + Utils.formatPrice(discount));

    }

    @Override
    public void initData() {
        detailsOrderAdapter = new DetailsOrderAdapter(this);
        shoppingBagViewModel = new ViewModelProvider(this).get(ShoppingBagViewModel.class);
        shoppingBagViewModel.getDetailOrders();
    }

    @Override
    public void initView() {
        binding.rcvShoppingBag.setAdapter(detailsOrderAdapter);
        binding.checkBox.setOnCheckedChangeListener((compoundButton, b) -> {
            if (checkOneSelected) {
                shoppingBagViewModel.selectALl(b);
                isSelectedAll = b;
            }
        });


        binding.purchase.setOnClickListener(view -> {
            isScoll = false;

            if (!isClickPurchase) shoppingBagViewModel.getDetailOrders();
        });
    }

    @Override
    public FragmentShoppingBagBinding getViewBinding(View view) {
        return FragmentShoppingBagBinding.bind(view);
    }


    @Override
    public void onMinusClick(DetailOrderResponse detailOrderResponse) {
        shoppingBagViewModel.updateDetailOrders(createDetailsOrderRequest(detailOrderResponse));
    }

    @Override
    public void onPlusClick(DetailOrderResponse detailOrderResponse) {
        shoppingBagViewModel.updateDetailOrders(createDetailsOrderRequest(detailOrderResponse));
    }

    @Override
    public void unconditional(int position) {
        if (!isScoll) {
            binding.rcvShoppingBag.scrollToPosition(position);
            isScoll = true;
        }
    }

    @Override
    public void onCloseClick(DetailOrderResponse detailOrderResponse) {
        idDetailOrder = detailOrderResponse.get_id();
        shoppingBagViewModel.deleteDetailsOrder(detailOrderResponse.get_id());
    }

    @Override
    public void onCheckedClick(DetailOrderResponse detailOrderResponse) {
        shoppingBagViewModel.updateDetailOrders(createDetailsOrderRequest(detailOrderResponse));
    }

    private DetailOrderRequest createDetailsOrderRequest(DetailOrderResponse
                                                                 detailOrderResponse) {
        return new DetailOrderRequest(detailOrderResponse.get_id(), detailOrderResponse.getQuantity(), detailOrderResponse.getSelected());
    }

}