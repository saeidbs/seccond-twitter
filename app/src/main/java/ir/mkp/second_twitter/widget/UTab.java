package ir.mkp.second_twitter.widget;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class UTab {

  public static class Item {
    private Fragment fragment;
    private String title;
    private int icon;

    public Item(Class<? extends Fragment> fragmentClass, String title, int icon) {
      try {
        this.fragment = fragmentClass.newInstance();
        this.title = title;
        this.icon = icon;
      } catch (InstantiationException e) {
        e.printStackTrace();
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      }
    }

    public Item(Fragment fragment, String title, int icon) {
      this.fragment = fragment;
      this.title = title;
      this.icon = icon;
    }

    public Fragment getFragment() {
      return fragment;
    }

    public String getTitle() {
      return title;
    }

    public int getIcon() {
      return icon;
    }

    public void setTitle(String value) {
      title = value;
    }

    public void setIcon(int value) {
      icon = value;
    }
  }

  public class Adapter extends FragmentPagerAdapter {
    private List<Item> items = new ArrayList<>();

    public Adapter(FragmentManager fragmentManager) {
      super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
      return items.get(position).getFragment();
    }

    public Item getRawItem(int position) {
      return items.get(position);
    }

    @Override
    public int getCount() {
      return items.size();
    }

    public void add(Item item) {
      items.add(item);
    }

    @Override
    public CharSequence getPageTitle(int position) {
      return items.get(position).getTitle();
    }
  }

  private ViewPager viewPager;
  private TabLayout tabLayout;
  private Adapter adapter;


  public UTab(AppCompatActivity activity, int viewPagerId, int tabLayoutId) {
    View view = activity.getWindow().getDecorView();
    viewPager = (ViewPager) view.findViewById(viewPagerId);
    tabLayout = (TabLayout) view.findViewById(tabLayoutId);
    adapter = new Adapter(activity.getSupportFragmentManager());
    viewPager.setAdapter(adapter);
    tabLayout.setupWithViewPager(viewPager);
  }

  private void refreshIcons() {
    for (int i=0; i<adapter.getCount(); i++) {
      int itemIcon = adapter.getRawItem(i).getIcon();
      if (itemIcon != 0) {
        tabLayout.getTabAt(i).setIcon(adapter.getRawItem(i).getIcon());
      }
    }
  }

  public void add(Class<? extends Fragment> fragmentClass, String title, int icon) {
    Item item = new Item(fragmentClass, title, icon);
    adapter.add(item);
    adapter.notifyDataSetChanged();

    refreshIcons();
  }

  public void add(Class<? extends Fragment> fragmentClass, String title) {
    add(fragmentClass, title, 0);
  }

  public void add(Class<? extends Fragment> fragmentClass, int icon) {
    add(fragmentClass, null, icon);
  }

  public void setIcon(int index, int icon) {
    adapter.getRawItem(index).setIcon(icon);
    refreshIcons();
  }

  public void setTitle(int index, String title) {
    adapter.getRawItem(index).setTitle(title);
    adapter.notifyDataSetChanged();
    refreshIcons();
  }
}
