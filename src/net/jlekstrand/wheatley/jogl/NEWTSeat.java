/* * Copyright © 2012-2013 Jason Ekstrand.
 *  
 * Permission to use, copy, modify, distribute, and sell this software and its
 * documentation for any purpose is hereby granted without fee, provided that
 * the above copyright notice appear in all copies and that both that copyright
 * notice and this permission notice appear in supporting documentation, and
 * that the name of the copyright holders not be used in advertising or
 * publicity pertaining to distribution of the software without specific,
 * written prior permission.  The copyright holders make no representations
 * about the suitability of this software for any purpose.  It is provided "as
 * is" without express or implied warranty.
 * 
 * THE COPYRIGHT HOLDERS DISCLAIM ALL WARRANTIES WITH REGARD TO THIS SOFTWARE,
 * INCLUDING ALL IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS, IN NO
 * EVENT SHALL THE COPYRIGHT HOLDERS BE LIABLE FOR ANY SPECIAL, INDIRECT OR
 * CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING FROM LOSS OF USE,
 * DATA OR PROFITS, WHETHER IN AN ACTION OF CONTRACT, NEGLIGENCE OR OTHER
 * TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION WITH THE USE OR PERFORMANCE
 * OF THIS SOFTWARE.
 */
package net.jlekstrand.wheatley.jogl;

import net.jlekstrand.wheatley.*;

import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.newt.event.MouseListener;
import com.jogamp.newt.event.MouseEvent;

import org.freedesktop.wayland.Fixed;
import org.freedesktop.wayland.protocol.wl_pointer;
import org.freedesktop.wayland.protocol.wl_seat;

class NEWTSeat extends Seat
{
    private class MouseHandler implements MouseListener
    {
        public MouseHandler()
        { }
    
        @Override
        public void mouseClicked(MouseEvent event)
        { }
    
        @Override
        public void mouseDragged(MouseEvent event)
        { }
    
        @Override
        public void mouseEntered(MouseEvent event)
        { }
    
        @Override
        public void mouseExited(MouseEvent event)
        { }
    
        @Override
        public void mouseMoved(MouseEvent event)
        {
            pointer.handleMotion((int)event.getWhen(), new Fixed(event.getX()),
                    new Fixed(event.getY()));
        }
    
        @Override
        public void mousePressed(MouseEvent event)
        {
            pointer.handleButton(compositor.display.getSerial(),
                    (int)event.getWhen(), event.getButton(),
                    wl_pointer.BUTTON_STATE_PRESSED);
        }
    
        @Override
        public void mouseReleased(MouseEvent event)
        {
            pointer.handleButton(compositor.display.getSerial(),
                    (int)event.getWhen(), event.getButton(),
                    wl_pointer.BUTTON_STATE_RELEASED);
        }
    
        @Override
        public void mouseWheelMoved(MouseEvent event)
        {
            pointer.handleAxis((int)event.getWhen(),
                    wl_pointer.AXIS_VERTICAL_SCROLL,
                    new Fixed(event.getWheelRotation()));
        }
    }

    public NEWTSeat(Compositor compositor, GLWindow window)
    {
        super(compositor, wl_seat.CAPABILITY_POINTER);
        window.addMouseListener(new MouseHandler());
    }
}

