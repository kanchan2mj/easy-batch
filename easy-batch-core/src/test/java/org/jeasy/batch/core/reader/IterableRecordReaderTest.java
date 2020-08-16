/*
 * The MIT License
 *
 *   Copyright (c) 2020, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 *
 *   Permission is hereby granted, free of charge, to any person obtaining a copy
 *   of this software and associated documentation files (the "Software"), to deal
 *   in the Software without restriction, including without limitation the rights
 *   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   copies of the Software, and to permit persons to whom the Software is
 *   furnished to do so, subject to the following conditions:
 *
 *   The above copyright notice and this permission notice shall be included in
 *   all copies or substantial portions of the Software.
 *
 *   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *   THE SOFTWARE.
 */
package org.jeasy.batch.core.reader;

import org.jeasy.batch.core.record.GenericRecord;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Iterator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(value = MockitoJUnitRunner.class)
public class IterableRecordReaderTest {

    private static final String RECORD = "Foo";

    @Mock
    private Iterable<String> dataSource;

    @Mock
    private Iterator<String> iterator;

    private IterableRecordReader<String> iterableRecordReader;

    @Before
    public void setUp() {
        when(dataSource.iterator()).thenReturn(iterator);
        when(iterator.hasNext()).thenReturn(true);
        when(iterator.next()).thenReturn(RECORD);
        iterableRecordReader = new IterableRecordReader<>(dataSource);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenDataSourceIsNull_thenShouldThrowAnIllegalArgumentException() {
        iterableRecordReader = new IterableRecordReader<>(null);
    }

    @Test
    public void testReadRecord() {
        GenericRecord<String> genericRecord = iterableRecordReader.readRecord();
        assertThat(genericRecord).isNotNull();
        assertThat(genericRecord.getPayload()).isEqualTo(RECORD);
    }

}
