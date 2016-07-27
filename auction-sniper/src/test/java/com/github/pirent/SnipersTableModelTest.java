package com.github.pirent;

import static javax.swing.event.TableModelEvent.ALL_COLUMNS;
import static javax.swing.event.TableModelEvent.INSERT;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.hamcrest.Matcher;
import org.hamcrest.beans.SamePropertyValuesAs;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.github.pirent.ui.SnipersTableModel;

@RunWith(MockitoJUnitRunner.class)
public class SnipersTableModelTest {
	
	@Mock
	private TableModelListener listener;
	
	private SnipersTableModel model = new SnipersTableModel();
	
	@Before
	public void attachModelListener() {
		model.addTableModelListener(listener);
	}
	
	@Test
	public void hasEnoughColumns() {
		assertThat(model.getColumnCount(), equalTo(Column.values().length));
	}
	
	@Test
	public void setsSniperValuesInColumns() {
		SniperSnapshot joining = SniperSnapshot.joining("item id");
		SniperSnapshot bidding = joining.bidding(555, 666);
		
		model.addSniper(joining);
		model.sniperStateChanged(bidding);
//		verify(listener).tableChanged(argThat(is(aChangeInRow(1))));
		verify(listener, times(2)).tableChanged(any(TableModelEvent.class));
		assertRowMatchesSnapshot(0, bidding);
	}

	/*
	 * Due to the mixin of Hamcrest version dependency,
	 * org.hamcrest.bean.SamePropertyValueAs is not working as expected.
	 */
	@SuppressWarnings("unused")
	private Matcher<TableModelEvent> aChangeInRow(int rowIndex) {
		return new SamePropertyValuesAs<TableModelEvent>(new TableModelEvent(
				model, rowIndex, rowIndex, ALL_COLUMNS, INSERT));
	}

	@Test
	public void setsUpColumnHeadings() {
		for (Column column : Column.values()) {
			assertEquals(column.name, model.getColumnName(column.ordinal()));
		}
	}
	
	@Test
	public void notifiesListenersWhenAddingAAsniper() {
		SniperSnapshot joining = SniperSnapshot.joining("item123");
		assertThat(model.getRowCount(), is(0));
		
		model.addSniper(joining);
//		verify(listener).tableChanged(argThat(is(anInsertionAtRow(1))));
		verify(listener).tableChanged(any(TableModelEvent.class));
		
		assertThat(model.getRowCount(), is(1));
		assertRowMatchesSnapshot(0, joining);
	}

	/*
	 * Due to the mixin of Hamcrest version dependency,
	 * org.hamcrest.bean.SamePropertyValueAs is not working as expected
	 */
	@SuppressWarnings("unused")
	private Matcher<TableModelEvent> anInsertionAtRow(int rowIndex) {
		return new SamePropertyValuesAs<TableModelEvent>(new TableModelEvent(
				model, rowIndex, rowIndex, TableModelEvent.ALL_COLUMNS,
				TableModelEvent.INSERT));
	}

	private void assertRowMatchesSnapshot(int rowIndex, SniperSnapshot snapshot) {
		for (Column column : Column.values()) {
			assertThat(model.getValueAt(rowIndex, column.ordinal()),
					is(column.valueIn(snapshot)));
		}
	}
	
	@Test
	public void holdSnipersInAdditionOrder() {
		model.addSniper(SniperSnapshot.joining("item 0"));
		model.addSniper(SniperSnapshot.joining("item 1"));
		
		assertEquals("item 0", cellValue(0, Column.ITEM_IDENTIFIER));
		assertEquals("item 1", cellValue(1, Column.ITEM_IDENTIFIER));
	}

	private Object cellValue(int rowIndex, Column column) {
		return model.getValueAt(rowIndex, column.ordinal());
	}
	
	@Test
	public void updatesCorrectRowForSniper() {
		SniperSnapshot joiningItem0 = SniperSnapshot.joining("item 0");
		SniperSnapshot joiningItem1 = SniperSnapshot.joining("item 1");
		model.addSniper(joiningItem0);
		model.addSniper(joiningItem1);
		
		SniperSnapshot biddingItem1 = joiningItem1.bidding(500, 520);
		model.sniperStateChanged(biddingItem1);
		
		assertRowMatchesSnapshot(1, biddingItem1);
		assertRowMatchesSnapshot(0, joiningItem0);
	}
	
	@Test(expected=Defect.class)
	public void throwsDefectIfNotExistingSniperForAnUpdate() {
		SniperSnapshot joiningItem0 = SniperSnapshot.joining("item 0");
		SniperSnapshot joiningItem1 = SniperSnapshot.joining("item 1");
		
		model.addSniper(joiningItem0);
		model.sniperStateChanged(joiningItem1.bidding(500, 520));
	}
}
